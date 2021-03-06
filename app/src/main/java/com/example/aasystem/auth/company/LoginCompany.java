package com.example.aasystem.auth.company;
import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aasystem.company.fragment.CompanyHome;
import com.example.aasystem.auth.ForgetPassword;
import com.example.aasystem.R;
import com.example.aasystem.user.model.UserCredential;
import com.example.aasystem.utils.SharedPrefMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginCompany extends AppCompatActivity
{
    EditText etLogGmail,etLoginPassword;
    Session session;
    Button btnLogin;
    TextView txForget, toReg;
    FirebaseAuth fAuth;
    DatabaseReference myRef;
    FirebaseDatabase mFirebaseDatabase;
    String usrid;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Instantiate firebase
        fAuth = FirebaseAuth.getInstance();
        myRef=FirebaseDatabase.getInstance().getReference("company");

        //Declaring variables
        etLogGmail = findViewById(R.id.etLogGmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        /**To register*/
        toReg=findViewById(R.id.reg_account);
        toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(LoginCompany.this, CompanyRegister.class);
                startActivity(i);
                finish();
            }
        });

        txForget=(TextView) findViewById(R.id.tvRegister);
        txForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rest = new Intent(LoginCompany.this, ForgetPassword.class);
                startActivity(rest);
                finish();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etLogGmail.getText().toString().trim();
                final String password = etLoginPassword.getText().toString().trim();
                 if(email.toLowerCase().contains("@company"))
                {
                   if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
                   {
                       etLogGmail.setError("This field is Required");
                       etLoginPassword.setError("This field is Required");
                       return;
                   }
                   else
                   {
                       fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful())
                               {
                                   SharedPrefMethods sharedPrefMethods = new SharedPrefMethods(LoginCompany.this);
                                   UserCredential userCredential = new UserCredential(etLogGmail.getText().toString(), etLoginPassword.getText().toString(), "company");
                                   sharedPrefMethods.saveUserData(userCredential);
                                   startActivity(new Intent(getApplicationContext(), CompanyHome.class));
                                   finish();

                               }
                               else
                               {
                                   Toast.makeText(LoginCompany.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
                   }
                }
                else
                {
                    Toast.makeText(LoginCompany.this, "Unauthorized user ! " , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
