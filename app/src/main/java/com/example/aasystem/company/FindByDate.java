package com.example.aasystem.company;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aasystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FindByDate extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser muser;
    ListView users;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    private String userid, usr, Time, leaveTime, coming;
    ImageButton timebtn, fingerprintbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records_date);

        users = findViewById(R.id.users);
        Button datbtn = findViewById(R.id.datbtn);
        timebtn = findViewById(R.id.timebtn);
        fingerprintbtn = findViewById(R.id.fingerprintbtn);


        datbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText dateinpt = findViewById(R.id.dateinpt);

                auth = FirebaseAuth.getInstance();
                muser = auth.getCurrentUser();
                userid = muser.getUid();

                DatabaseReference myref = FirebaseDatabase.getInstance().getReference("company").child("Users");
                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, arrayList);
                users.setAdapter(arrayAdapter);

                myref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String date = dateinpt.getText().toString();

                        usr = String.valueOf(dataSnapshot.child("user_name").getValue());
                        Time = String.valueOf(dataSnapshot.child("figerPrint").child(date).child("Time").getValue());
                        leaveTime = String.valueOf(dataSnapshot.child("figerPrint").child(date).child("leaveTime").getValue());

                        String b = usr + " " + Time + " " + leaveTime;
                        arrayList.add(b);
                        arrayAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });


        timebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        final EditText dateinpt = findViewById(R.id.dateinpt);

        auth = FirebaseAuth.getInstance();
        muser = auth.getCurrentUser();
        userid = muser.getUid();

        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("company").child("Users");
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, arrayList);
        users.setAdapter(arrayAdapter);

        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String date = dateinpt.getText().toString();

                usr = String.valueOf(dataSnapshot.child("user_name").getValue());
                Time = String.valueOf(dataSnapshot.child("figerPrint").child(date).child("Time").getValue());
                leaveTime = String.valueOf(dataSnapshot.child("figerPrint").child(date).child("leaveTime").getValue());

                String b = usr + " " + Time + " " + leaveTime;
                arrayList.add(b);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            }
        });

        fingerprintbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            final EditText dateinpt = findViewById(R.id.dateinpt);

            auth = FirebaseAuth.getInstance();
            muser = auth.getCurrentUser();
            userid = muser.getUid();

            DatabaseReference myref = FirebaseDatabase.getInstance().getReference("company").child("Users");
            arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, arrayList);
            users.setAdapter(arrayAdapter);

            myref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String date = dateinpt.getText().toString();

                    usr = String.valueOf(dataSnapshot.child("user_name").getValue());
                    coming = String.valueOf(dataSnapshot.child("figerPrint").child(date).child("Check").getValue());

                    String b = usr + " " + coming;
                    arrayList.add(b);
                    arrayAdapter.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            }
        });

    }
}