package com.example.aasystem.auth.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aasystem.R;
import com.example.aasystem.auth.ForgetPassword;
import com.example.aasystem.user.UserNav;
import com.example.aasystem.user.model.UserCredential;
import com.example.aasystem.utils.SharedPrefMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUser extends AppCompatActivity {

    EditText etLogGmail,etLoginPassword;
    Button btnLogin;
    TextView txForget, toReg;
    FirebaseAuth fAuth;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        //Instantiate firebase
        fAuth = FirebaseAuth.getInstance();

        //Declaring variables
        etLogGmail = findViewById(R.id.etLogGmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        txForget=(TextView) findViewById(R.id.tvRegister);
        txForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rest = new Intent(LoginUser.this, ForgetPassword.class);
                startActivity(rest);
            }
        });
        /**To register page*/
        toReg=findViewById(R.id.reg_account);
        toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(LoginUser.this, UserRegister.class);
                startActivity(i);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDoalog = new ProgressDialog(LoginUser.this);
                progressDoalog.setMessage("Loading....");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDoalog.show();
                final String email = etLogGmail.getText().toString().trim();
                final String password = etLoginPassword.getText().toString().trim();
                if(! email.toLowerCase().contains("@company"))
                {
                    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
                    {
                        etLogGmail.setError("This field is Required");
                        etLoginPassword.setError("This field is Required");
                        return;
                    }
                    else
                    {
                        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if(task.isSuccessful())
                                {
                                    SharedPrefMethods sharedPrefMethods = new SharedPrefMethods(LoginUser.this);
                                    UserCredential userCredential = new UserCredential(etLogGmail.getText().toString(),
                                            etLoginPassword.getText().toString(), "user");
                                    sharedPrefMethods.saveUserData(userCredential);
                                    startActivity(new Intent(getApplicationContext(), UserNav.class));
                                    progressDoalog.dismiss();

                                }
                                else
                                {
                                    Toast.makeText(LoginUser.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDoalog.dismiss();
                                }
                            }
                        });
                    }
                }
                else
                {
                    Toast.makeText(LoginUser.this, "Unauthorized user ! " , Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();

                }
            }
        });
    }
}