package com.example.aasystem.company;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aasystem.FingerPrintModel;
import com.example.aasystem.PendingUsers;
import com.example.aasystem.R;
import com.example.aasystem.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements UsersAdapter.ItemClickListener
{
    ImageButton prendingbtn;
    Button notificationbtn;
    private FirebaseAuth auth;
    private FirebaseUser muser;
    private String userid,name,attendTime,leaveTime,check;

    RecyclerView recyclerView;

    UsersAdapter usersAdapter;
    List<FingerPrintModel> usersList = new ArrayList<>();

    public HomeFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ListView users =  v.findViewById(R.id.usr);

        auth = FirebaseAuth.getInstance();
        muser = auth.getCurrentUser();
        userid = muser.getUid();

        DateFormat formatt = new SimpleDateFormat("yyyy/MM/dd");
        long de = System.currentTimeMillis();
        final String date = formatt.format(new Date(de));

        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("company").child("fingerprint");
        recyclerView = (RecyclerView) v.findViewById(R.id.users_recyclerview);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        myref.addValueEventListener(new ValueEventListener()
        {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot)
           {
               for (DataSnapshot snapshot : dataSnapshot.getChildren())
               {
                   /*name = String.valueOf(snapshot.child("user_name").getValue());
                   attendTime = String.valueOf(snapshot.child("figerPrint").child(date).child("Time").getValue());
                   leaveTime = String.valueOf(snapshot.child("figerPrint").child(date).child("leaveTime").getValue());
                   check = String.valueOf(snapshot.child("figerPrint").child(date).child("Check").getValue());
                    */
                   Log.d("attendance", "fingerprint");
                   Log.d("attendance", String.valueOf(snapshot.child("name").getValue()));
                   FingerPrintModel userModel = snapshot.getValue(FingerPrintModel.class);
                   usersList.add(userModel);

               }

               usersAdapter = new UsersAdapter(getActivity(), usersList);
               recyclerView.setAdapter(usersAdapter);
               usersAdapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


   /*     myref.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                usr = String.valueOf(dataSnapshot.child("user_name").getValue());
                Time = String.valueOf(dataSnapshot.child("figerPrint").child(date).child("Time").getValue());
                leaveTime = String.valueOf(dataSnapshot.child("figerPrint").child(date).child("leaveTime").getValue());
                Check = String.valueOf(dataSnapshot.child("figerPrint").child(date).child("Check").getValue());

                String b = usr + " " + Time + " " + leaveTime + " " + Check;
                arrayList.add(b);
                arrayAdapter.notifyDataSetChanged();

                UserModel userModel = new UserModel();
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
        });*/

        prendingbtn= v.findViewById(R.id.bellbtn);
        prendingbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(), PendingUsers.class);
                startActivity(i);
            }
        });

        notificationbtn = v.findViewById(R.id.notificationbtn);
        notificationbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://console.firebase.google.com/u/2/project/aasystem-f460e/notification"));
               startActivity(i);
            }
        });

        return v;
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), RecordsFragment.class);
        intent.putExtra("user_id", usersList.get(position).getId());
        getActivity().startActivity(intent);
    }
}
