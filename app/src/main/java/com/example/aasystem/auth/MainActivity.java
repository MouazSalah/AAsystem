package com.example.aasystem.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.aasystem.R;
import com.example.aasystem.auth.user.LoginUser;
import com.example.aasystem.company.CompanyHome;
import com.example.aasystem.user.UserNav;
import com.example.aasystem.user.model.UserCredential;
import com.example.aasystem.utils.SharedPrefMethods;

public class MainActivity extends AppCompatActivity
{
    private static int SPLASH_TIME_OUT=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                SharedPrefMethods sharedPrefMethods = new SharedPrefMethods(MainActivity.this);
                UserCredential userCredential = sharedPrefMethods.getUserData();

                Intent homeIntent;
                if (sharedPrefMethods.getUserData() != null)
                {
                    if ("company".equals(userCredential.getType()))
                    {
                        homeIntent= new Intent(MainActivity.this, CompanyHome.class);
                        startActivity(homeIntent);
                        finish();
                    }
                    else if ("user".equals(userCredential.getType()))
                    {
                        homeIntent= new Intent(MainActivity.this, UserNav.class);
                        startActivity(homeIntent);
                        finish();
                    }
                    else
                    {
                        homeIntent= new Intent(MainActivity.this, AuthActivity.class);
                        startActivity(homeIntent);
                        finish();
                    }
                }
                else
                {
                    homeIntent= new Intent(MainActivity.this, AuthActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
    }
}
