package com.example.aasystem.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.aasystem.R;
import com.example.aasystem.auth.company.LoginCompany;
import com.example.aasystem.auth.user.LoginUser;

public class AuthActivity extends AppCompatActivity
{
    MaterialRippleLayout user, company;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        user = findViewById(R.id.signInAsUser);
        company = findViewById(R.id.signInAsCompany);

        user.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                startActivity(intent);
                finish();
            }
        });
        company.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), LoginCompany.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
