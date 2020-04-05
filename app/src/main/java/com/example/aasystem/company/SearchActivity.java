package com.example.aasystem.company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.aasystem.FingerPrintModel;
import com.example.aasystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity
{
    DatabaseReference myref;
    RecyclerView recyclerView;

    UsersAdapter usersAdapter;
    List<FingerPrintModel> usersList = new ArrayList<>();

    int selectedMonth;
    Spinner monthSpinner;
    List<Integer> monthList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        myref = FirebaseDatabase.getInstance().getReference("company").child("fingerprint");

        recyclerView = (RecyclerView) findViewById(R.id.search_recyclerview);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getAllUsers();

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
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, monthList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(dataAdapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l)
            {
                selectedMonth = (int) monthSpinner.getItemAtPosition(pos);
                getUsers();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    private void getAllUsers()
    {
        myref.addValueEventListener(new ValueEventListener()
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

    private void getUsers()
    {
        usersList.clear();

        myref.addValueEventListener(new ValueEventListener()
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
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
