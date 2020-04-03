package com.example.aasystem.company;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aasystem.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordsFragment extends Fragment {
Button btnDate,btnId;

    public RecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_records, container, false);
        btnId= v.findViewById(R.id.fbi);
        btnDate= v.findViewById(R.id.fbd);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FindByDate.class);
                startActivity(i);
            }

        });
        btnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FindById.class);
                startActivity(i);
            }

        });



        return  v;


    }

}
