package com.example.aasystem.auth.company;

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

import com.example.aasystem.auth.user.LoginUser;
import com.example.aasystem.user.model.UserCredential;
import com.example.aasystem.utils.CompanyAcountInfo;
import com.example.aasystem.company.DomainActivity;
import com.example.aasystem.R;
import com.example.aasystem.auth.RegisterSuccess;
import com.example.aasystem.utils.SharedPrefMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CompanyRegister extends AppCompatActivity {

    EditText mCompanyName,mEmail,mPassword,mPhone, mUsername;
    TextView companyName ,toLogin;;
    Button btnRegister , btnDomain;
    CheckBox checkbox_location ,checkbox_notify,checkbox_certify;
    FirebaseAuth mFirebase;
    String usrid;
    FirebaseUser user;// Give each user in ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyregister);

        final Intent i = getIntent();
        final String From = i.getStringExtra("From");
        final String To = i.getStringExtra("To");


           /**Database*/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("company").child("ComAc");
        mFirebase= FirebaseAuth.getInstance();

        /**Declaring variables*/
        mCompanyName= findViewById(R.id.etOrgname);
        mEmail      = findViewById(R.id.etRegEmail);
        mPassword   = findViewById(R.id.etRegPassword);
        mPhone      = findViewById(R.id.etRegPhone);
        mUsername     = findViewById(R.id.etUsername);
        btnRegister= findViewById(R.id.btnReg);
        checkbox_location=findViewById(R.id.checkbox_location);
        checkbox_notify=findViewById(R.id.checkbox_notify);
        checkbox_certify=findViewById(R.id.checkbox_certify);

        /** Domaain button */
        btnDomain = findViewById(R.id.btndomain);
        btnDomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CompanyRegister.this, DomainActivity.class);
                startActivity(i);
            }
        });

        /**Go to login page*/
        toLogin= findViewById(R.id.login_account);
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(CompanyRegister.this, LoginCompany.class);
                startActivity(i);
            }
        });



        //Register btn
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String CompanyName = mCompanyName.getText().toString().trim();
                final String Phone = mPhone.getText().toString().trim();
                final String Username = mUsername.getText().toString().trim();
                final String lat = "24.472209";
                final String lon = "39.568258";
                final String OnTime = "15:30";


                if(email.isEmpty()){
                    mEmail.setError("Email is Required.");
                    mEmail.requestFocus();
                }

                else if(password.length() < 6){
                    mPassword.setError("Password Must be at least Characters");
                    mPassword.requestFocus();
                }
                else if(! email.contains("@company")){
                    mPassword.setError("Email must end with @company.sa");
                    mPassword.requestFocus();

                }
                if (!checkbox_location.isChecked()){
                    Toast.makeText(CompanyRegister.this,"Make Select ,",Toast.LENGTH_LONG).show();
                    checkbox_location.setError("Error Must Check");
                    return ;

                }

                if (!checkbox_notify.isChecked()){
                    checkbox_notify.setError("Error Must Check");

                    return ;

                }
                if (!checkbox_certify.isChecked()){
                    checkbox_certify.setError("Error Must Check");
                    return ;

                }

                else if(!( email.isEmpty() && password.isEmpty()))
                {

                    // adding user to firebase
                    mFirebase.createUserWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                              // Store user information in the database
                                CompanyAcountInfo companyAcountInfo = new CompanyAcountInfo(email,password,CompanyName,Phone,Username,lat,lon,From,To,OnTime);

                                user = mFirebase.getCurrentUser();
                                usrid = user.getUid();


                                myRef.child(usrid).setValue(companyAcountInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful())
                                        {
                                            SharedPrefMethods sharedPrefMethods = new SharedPrefMethods(CompanyRegister.this);
                                            UserCredential userCredential = new UserCredential(mEmail.getText().toString(), mPassword.getText().toString(), "company");
                                            sharedPrefMethods.saveUserData(userCredential);
                                            startActivity(new Intent(CompanyRegister.this, RegisterSuccess.class));

                                        }
                                        else {

                                            Toast.makeText(CompanyRegister.this, "Could not register Admin.", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                });


                            } else
                                Toast.makeText(CompanyRegister.this, "Error on creating an account, please try again", Toast.LENGTH_SHORT).show();
                        }

                    });
                }
                else {
                    Toast.makeText(CompanyRegister.this, "Error occurred !", Toast.LENGTH_SHORT).show();

                }
            }
        });

        TextView textView = findViewById(R.id.login_account);
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), LoginCompany.class);
                startActivity(intent);
            }
        });
    }

}
