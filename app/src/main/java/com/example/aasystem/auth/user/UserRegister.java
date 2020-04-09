package com.example.aasystem.auth.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aasystem.R;
import com.example.aasystem.auth.fingerprintActivity;
import com.example.aasystem.user.activities.FingerPrintActivity;
import com.example.aasystem.user.model.UserAcountInfo;
import com.example.aasystem.user.fragment.UserNav;
import com.example.aasystem.user.model.UserCredential;
import com.example.aasystem.utils.SharedPrefMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;


public class UserRegister extends AppCompatActivity {

    EditText userId, phoneNum, userName,email, password;
    TextView companyName, toLogin;
    Button  btnURegister, gotofinger ;
    FirebaseAuth mFirebase;
    CheckBox checkbox_location ,checkbox_notify,checkbox_certify;
    String key;
    FirebaseUser user;// لوضع اي دي لكل يوسر
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        // تعيئة حقل الفينجر برنت
        final Intent i = getIntent();
        final String figerPrint = i.getStringExtra("figerPrint");
        // تثبيت وقت التسجيل في البرنامج
        Date date = new Date();
        SimpleDateFormat strdate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        final String stringdate = strdate.format(date);
        // تهيئة الداتا بيس
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("company");

        mFirebase= FirebaseAuth.getInstance();
        companyName = findViewById(R.id.orgname);
        userId= findViewById (R.id.putuserid);
        phoneNum = findViewById(R.id.putUserPhone);
        email= findViewById(R.id.putUserEmail);
        userName= findViewById(R.id.putUsername);
        password=findViewById(R.id.putRegPassword);
        btnURegister=findViewById(R.id.btnReg);
        checkbox_location=findViewById(R.id.checkbox_location);
        checkbox_notify=findViewById(R.id.checkbox_notify);
        checkbox_certify=findViewById(R.id.checkbox_certify);

        /**go to finger page Button*/

        gotofinger= findViewById(R.id.gotofinger);
        gotofinger.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(UserRegister.this, fingerprintActivity.class);
                startActivity(i);
                finish();
            }
        });
        /**Go to login page*/
        toLogin= findViewById(R.id.login_account);
        toLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i= new Intent(UserRegister.this, LoginUser.class);
                startActivity(i);
                finish();
            }
        });

        //Register btn
        btnURegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String user_Id= userId.getText().toString().trim();
                final String phone_Num = phoneNum.getText().toString().trim();
                final String e_mail= email.getText().toString().trim();
                final String user_name= userName.getText().toString().trim();
                final String passwd = password.getText().toString().trim();

                if(user_Id.isEmpty())
                {
                    userId.setError("Please enter your ID");
                    return ;
                    //userId.requestFocus();
                }
                else if(phone_Num.isEmpty())
                {
                    phoneNum.setError("Please enter phone number");
                    return ;                }
                else if(e_mail.isEmpty())
                {
                    email.setError("Please enter an email");
                    return ;                }
                else if(user_name.isEmpty())
                {
                    userName.setError("Please enter your  username");
                    return ;                }
                else if(passwd.length() < 6)
                {
                    password.setError("password must have more than 6 Characters");
                    return ;
                }
                if (!checkbox_location.isChecked())
                {
                    Toast.makeText(UserRegister.this,"Make Select ,",Toast.LENGTH_LONG).show();
                    checkbox_location.setError("Error Must Check");
                    return ;
                }
                if (!checkbox_notify.isChecked())
                {
                    checkbox_notify.setError("Error Must Check");
                    return ;
                }
                if (!checkbox_certify.isChecked())
                {
                    checkbox_certify.setError("Error Must Check");
                    return ;
                }
                else if(!( phone_Num.isEmpty() && e_mail.isEmpty() && user_name.isEmpty() && user_Id.isEmpty() &&passwd.isEmpty()))
                {
                    progressDoalog = new ProgressDialog(UserRegister.this);
                    progressDoalog.setMessage("Loading....");
                    progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDoalog.show();

                    // adding user to firebase
                    mFirebase.createUserWithEmailAndPassword(e_mail, passwd).addOnCompleteListener( new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                // هذه الدالة لوضع معلومات اليوسر في الداتا بيس
                                user = mFirebase.getCurrentUser();
                                key = user.getUid();
                                UserAcountInfo userAcountInfo =new UserAcountInfo( key, e_mail,
                                                                        user_Id, user_name, phone_Num,
                                                                        passwd,figerPrint,stringdate);

                                myRef.child("Users").child(key).setValue(userAcountInfo).addOnCompleteListener(new OnCompleteListener<Void>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if(task.isSuccessful())
                                        {
                                            SharedPrefMethods sharedPrefMethods = new SharedPrefMethods(UserRegister.this);
                                            UserCredential userCredential = new UserCredential(email.getText().toString(),
                                                    password.getText().toString(),
                                                    "user");
                                            sharedPrefMethods.saveUserData(userCredential);
                                            startActivity(new Intent(UserRegister.this, UserNav.class));
                                            finish();
                                            progressDoalog.dismiss();
                                        }
                                        else
                                        {
                                            Toast.makeText(UserRegister.this, "Could not register User.", Toast.LENGTH_SHORT).show();
                                            progressDoalog.dismiss();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                progressDoalog.dismiss();
                                Toast.makeText(UserRegister.this, "Error on creating an account, please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    progressDoalog.dismiss();
                    Toast.makeText(UserRegister.this, "Error occurred !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
