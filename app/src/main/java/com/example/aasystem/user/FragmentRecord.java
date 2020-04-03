package com.example.aasystem.user;

import android.app.DatePickerDialog;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aasystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static androidx.fragment.app.DialogFragment.STYLE_NORMAL;


public class FragmentRecord extends Fragment {

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private String userid;
    private FirebaseAuth auth;
    private FirebaseUser muser;

    public FragmentRecord() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rec = inflater.inflate(R.layout.fragment_u_record, container, false);

        final ListView info = rec.findViewById(R.id.info);
        final EditText in_from = rec.findViewById(R.id.in_from);
        Button btn_from =  rec.findViewById(R.id.btn_from);

        btn_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String date = in_from.getText().toString();
                auth = FirebaseAuth.getInstance();
                muser = auth.getCurrentUser();
                userid = muser.getUid();

                final DatabaseReference myref = FirebaseDatabase.getInstance().getReference("company").child("Users").child(userid).child("figerPrint").child(date);
                arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1,arrayList);
                info.setAdapter(arrayAdapter);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String t = String.valueOf(dataSnapshot.child("Time").getValue());
                        String le = String.valueOf(dataSnapshot.child("leaveTime").getValue());
                        String ch = String.valueOf(dataSnapshot.child("Check").getValue());
                        String d = String.valueOf(dataSnapshot.child("Date").getValue());

                        String b = ch + " " + t + " " + le + " " + d;

                        arrayList.add(b);

                        arrayAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                myref.addListenerForSingleValueEvent(valueEventListener);


            }
        });

        return rec;
    }


}





