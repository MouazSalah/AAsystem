package com.example.aasystem.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aasystem.company.CompanyHome;
import com.example.aasystem.R;


public class loginSuccess extends AppCompatActivity {
        Button btnHome;


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login_sucess);

            btnHome= findViewById(R.id.btnHome);
            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent(loginSuccess.this , CompanyHome.class);
                    startActivity(i);
                }
            });
        }
    }

