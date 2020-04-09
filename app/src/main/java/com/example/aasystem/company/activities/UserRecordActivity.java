package com.example.aasystem.company.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.aasystem.FingerPrintModel;
import com.example.aasystem.R;
import com.example.aasystem.company.adapter.UsersAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserRecordActivity extends AppCompatActivity {

    ImageButton prendingbtn;
    Button notificationbtn;
    private String name,attendTime,leaveTime,check;

    RecyclerView recyclerView;

    String userId;
    UsersAdapter usersAdapter;
    List<FingerPrintModel> usersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_record);

        userId = getIntent().getStringExtra("user_id");

        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("company").child("Users");
        recyclerView = (RecyclerView) findViewById(R.id.userrecords_recyclerview);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        myref.child(userId).child("fingerprint").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    FingerPrintModel userModel = snapshot.getValue(FingerPrintModel.class);
                    usersList.add(userModel);
                }

                usersAdapter = new UsersAdapter(getApplicationContext(), usersList);
                recyclerView.setAdapter(usersAdapter);
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
