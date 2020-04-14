package com.example.aasystem.company.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.aasystem.FingerPrintModel;
import com.example.aasystem.R;
import com.example.aasystem.company.adapter.UsersAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserRecordsActivity extends AppCompatActivity
{
    DatabaseReference myref;
    RecyclerView recyclerView;
    UsersAdapter usersAdapter;
    List<FingerPrintModel> usersList = new ArrayList<>();
    int selectedMonth;
    Spinner monthSpinner;
    List<Integer> monthList;
    private FirebaseAuth auth;
    private FirebaseUser muser;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_records);

        myref = FirebaseDatabase.getInstance().getReference("company").child("Users");

        userId = getIntent().getStringExtra("user_id");

        recyclerView = (RecyclerView) findViewById(R.id.pendinguserrecords_recyclerview);

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


        monthList = new ArrayList<Integer>();
        monthList.add(1);
        monthList.add(2);
        monthList.add(3);
        monthList.add(4);
        monthList.add(5);
        monthList.add(6);
        monthList.add(7);
        monthList.add(8);
        monthList.add(9);
        monthList.add(10);
        monthList.add(11);
        monthList.add(12);


        monthSpinner = (Spinner) findViewById(R.id.month_spinner);
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_item, monthList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(dataAdapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l)
            {
                selectedMonth = (int) monthSpinner.getItemAtPosition(pos);
                getDataByMonth();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    private void getDataByMonth()
    {
        usersList.clear();

        myref.child(userId).child("fingerprint").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    FingerPrintModel userModel = snapshot.getValue(FingerPrintModel.class);

                    if (userModel.getMonth() == selectedMonth)
                    {
                        usersList.add(userModel);
                    }
                }
                usersAdapter = new UsersAdapter(getApplicationContext(), usersList);
                recyclerView.setAdapter(usersAdapter);
                usersAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}
