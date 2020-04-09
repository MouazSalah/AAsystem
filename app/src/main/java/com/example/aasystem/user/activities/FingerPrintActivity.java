
package com.example.aasystem.user.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aasystem.FingerPrintModel;
import com.example.aasystem.R;
import com.example.aasystem.user.fragment.UserNav;
import com.example.aasystem.utils.BiometricCheck;
import com.example.aasystem.utils.BiometricListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FingerPrintActivity extends AppCompatActivity
{
    Button button_authenticate;
    private FirebaseAuth auth;
    private FirebaseUser muser;
    private String userid,checkFinger,chkk;
    int year, month, day;
    String name;
    DatabaseReference databaseRef;

    FingerPrintModel fingerPrintModel;
    String status;

    String checkNumber;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprint);

        auth = FirebaseAuth.getInstance();
        muser = auth.getCurrentUser();
        userid = muser.getUid();

        DateFormat formatt = new SimpleDateFormat("yyyy/MM/dd");
        long de = System.currentTimeMillis();
        date = formatt.format(new Date(de));

        checkNumber = getIntent().getStringExtra("check");

        databaseRef = FirebaseDatabase.getInstance().getReference("company");
        databaseRef.child("Users").child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("user_name").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Calendar instance = Calendar.getInstance();
        month = instance.get(Calendar.MONTH) + 1;
        year = instance.get(Calendar.YEAR);
        day = instance.get(Calendar.DAY_OF_MONTH);

        button_authenticate = findViewById(R.id.fingerbtn);
        button_authenticate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //perform biometric check
                new BiometricCheck(FingerPrintActivity.this, listener);
            }
        });

        Button btnsv = (Button)findViewById(R.id.btnSave);
        btnsv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DateFormat formatt = new SimpleDateFormat("yyyy/MM/dd");
                long de = System.currentTimeMillis();
                final String date = formatt.format(new Date(de));

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                final String time = format.format(calendar.getTime());

                final String keyChild = year + "" + month + "" + day;

                int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);


                if (checkNumber.equals("first"))
                {
                    if (hour24hrs < 9)
                    {
                        status = "present";
                    }
                    else
                    {
                        status = "late";
                    }
                     fingerPrintModel = new FingerPrintModel(
                            date, userid, name, time,
                            "doesn't leave yet",
                            "100%",
                            "50%",
                            status,
                            year, month, day);
                    databaseRef.child("Users").child(userid).child("fingerprint").child(keyChild).setValue(fingerPrintModel).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                databaseRef.child("fingerprint").child(userid + "" + keyChild).setValue(fingerPrintModel);
                                Toast.makeText(FingerPrintActivity.this, "Thanks", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(FingerPrintActivity.this, UserNav.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    });
                }
                if (checkNumber.equals("second"))
                {
                    if (hour24hrs < 15)
                    {
                        status = "late";
                    }
                    else
                    {
                        status = "present";
                    }

                    databaseRef.child("Users").child(userid).child("fingerprint").child(keyChild).child("leave").setValue(time);
                    databaseRef.child("Users").child(userid).child("fingerprint").child(keyChild).child("second_check").setValue("100%").addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                databaseRef.child("fingerprint").child(userid + "" + keyChild).child("leave").setValue(time);
                                databaseRef.child("fingerprint").child(userid + "" + keyChild).child("second_check").setValue("100%");

                                Toast.makeText(FingerPrintActivity.this, "Thanks", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(FingerPrintActivity.this, UserNav.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    BiometricListener listener = new BiometricListener()
    {
        @Override
        public void onSuccess()
        {
            Toast.makeText(FingerPrintActivity.this, "User authentication successful",Toast.LENGTH_LONG).show();
            //turn button text green
            button_authenticate.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        }
        @Override
        public void onFailed()
        {
            //turn button text red
            button_authenticate.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    };
}

