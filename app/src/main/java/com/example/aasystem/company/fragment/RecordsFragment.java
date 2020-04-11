package com.example.aasystem.company.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordsFragment extends Fragment {
    DatabaseReference myref;
    RecyclerView recyclerView;

    RecordsAdapter usersAdapter;
    List<FingerPrintModel> usersList = new ArrayList<>();

    int selectedMonth;
    Spinner monthSpinner;
    List<Integer> monthList;

    public RecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.fragment_records, container, false);

        myref = FirebaseDatabase.getInstance().getReference("company").child("fingerprint");

        recyclerView = (RecyclerView) v.findViewById(R.id.search_recyclerview);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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


        monthSpinner = (Spinner) v.findViewById(R.id.month_spinner);
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, monthList);
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


        return  v;

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

                usersAdapter = new RecordsAdapter(getActivity(), usersList);
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
                usersAdapter = new RecordsAdapter(getActivity(), usersList);
                recyclerView.setAdapter(usersAdapter);
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
