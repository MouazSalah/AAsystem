package com.example.aasystem.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.aasystem.R;
import com.example.aasystem.notyLeave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LeavTime extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser muser;
    private String userid;
    DatabaseReference fir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_success);


        DateFormat formatt = new SimpleDateFormat("yyyy/MM/dd");
        long d = System.currentTimeMillis();
        String date = formatt.format(new Date(d));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        final String time = format.format(calendar.getTime());

        auth = FirebaseAuth.getInstance();
        muser = auth.getCurrentUser();
        userid = muser.getUid();

        fir = FirebaseDatabase.getInstance().getReference("company");
        final DatabaseReference myref = fir.child("Users").child(userid).child("figerPrint").child(date).child("leaveTime");

        myref.setValue(time).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LeavTime.this, "Thanks :)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Calendar out = Calendar.getInstance();
        out.setTimeInMillis(System.currentTimeMillis());
        out.set(Calendar.HOUR_OF_DAY,21);
        out.set(Calendar.MINUTE,0);

        Intent i = new Intent(LeavTime.this, notyLeave.class);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,i,0);
        AlarmManager alM = (AlarmManager) getSystemService(ALARM_SERVICE);
        alM.setRepeating(AlarmManager.RTC_WAKEUP,out.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pi);


    }
}
