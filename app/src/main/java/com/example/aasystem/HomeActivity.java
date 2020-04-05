package com.example.aasystem;

import android.app.AlarmManager;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aasystem.auth.company.CompanyRegister;
import com.example.aasystem.auth.loginSucess;
import com.example.aasystem.auth.user.LoginUser;
import com.example.aasystem.auth.user.UserRegister;

import java.util.Calendar;


public class HomeActivity extends AppCompatActivity {

    Button btnCompantReg, btnLoginCom , btnLoginUsr , btnURegister ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**Login company*/

        btnLoginCom= findViewById(R.id.btnLoginCom);
        btnLoginCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logc= new Intent(HomeActivity.this, loginSucess.loginActivity.class);
                startActivity(logc);
            }
        });

        /**Login user*/

        btnLoginUsr= findViewById(R.id.btnLoginUsr);
        btnLoginUsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log= new Intent(HomeActivity.this, LoginUser.class);
                startActivity(log);
            }
        });

        /**Company Register Button*/

        btnCompantReg= findViewById(R.id.btnCompany);
        btnCompantReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, CompanyRegister.class);
                startActivity(i);
            }
        });

        /**User Register Button*/

        btnURegister= findViewById(R.id.btnUser);
        btnURegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, UserRegister.class);
                startActivity(i);
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,15);
        calendar.set(Calendar.MINUTE,0);

        Intent intent = new Intent(HomeActivity.this, notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }
}
