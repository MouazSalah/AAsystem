package com.example.aasystem.company;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.example.aasystem.R;
import com.example.aasystem.auth.company.CompanyRegister;

public class DomainActivity extends AppCompatActivity {

    Button btnSave;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.domain);


        btnSave= findViewById(R.id.btnSave);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// هنا حددنا الوقت لنقله الى الداتا بيس
                EditText From = (EditText)findViewById(R.id.from_hour_edit);
                EditText To = (EditText)findViewById(R.id.To_hour_edit);

                Intent i = new Intent(DomainActivity.this, CompanyRegister.class);
                i.putExtra("From",From.getText().toString());
                i.putExtra("To",To.getText().toString());

                startActivity(i);

            }
        });
    }

    public void gps(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}