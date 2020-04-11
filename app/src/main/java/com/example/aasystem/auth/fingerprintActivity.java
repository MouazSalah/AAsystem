package com.example.aasystem.auth;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aasystem.R;
import com.example.aasystem.auth.user.UserRegister;
import com.example.aasystem.utils.BiometricCheck;
import com.example.aasystem.utils.BiometricListener;

public class fingerprintActivity extends AppCompatActivity{

    Button button_authenticate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprint);

        button_authenticate = findViewById(R.id.fingerbtn);
        button_authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //perform biometric check
                new BiometricCheck(fingerprintActivity.this, listener);
            }
        });

        Button btnsv = (Button)findViewById(R.id.btnSave);
        btnsv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
// هنا وضعنا قيمة فارغة لتهيئة حقل الفينجر برنت في الداتا بيس
                String figerPrint = "";
                Intent i = new Intent(fingerprintActivity.this, UserRegister.class);
                i.putExtra("figerPrint",figerPrint);
                startActivity(i);

            }
        });


    }

    BiometricListener listener = new BiometricListener() {
        @Override
        public void onSuccess() {
            Toast.makeText(fingerprintActivity.this, "User authentication successful",Toast.LENGTH_LONG).show();

            //turn button text green
            button_authenticate.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        }

        @Override
        public void onFailed() {

            //turn button text red
            button_authenticate.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    };
}
