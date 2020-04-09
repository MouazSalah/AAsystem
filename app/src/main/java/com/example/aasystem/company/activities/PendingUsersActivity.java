package com.example.aasystem.company.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aasystem.FingerPrintModel;
import com.example.aasystem.R;
import com.example.aasystem.company.adapter.PendingAdapter;
import com.example.aasystem.company.fragment.CompanyHome;
import com.example.aasystem.company.model.PendingUserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PendingUsersActivity extends AppCompatActivity implements PendingAdapter.ClickListener
{
    DatabaseReference databaseRef;
    ArrayList<PendingUserModel> pendingList = new ArrayList<>();
    String key, name, email, password;
    RecyclerView recyclerView;
    PendingAdapter pendingAdapter;
    PendingUserModel currentModel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_users);

        recyclerView = (RecyclerView) findViewById(R.id.pendingusers_recyclerview);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        databaseRef = FirebaseDatabase.getInstance().getReference("company").child("Users");
        databaseRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    name = snapshot.child("user_name").getValue(String.class);
                    email = snapshot.child("e_mail").getValue(String.class);
                    password = snapshot.child("passwd").getValue(String.class);
                    key = snapshot.child("key").getValue(String.class);
                    PendingUserModel pendingUserModel = new PendingUserModel(key, name , email, password);
                    pendingList.add(pendingUserModel);

                    Log.d("lognotes : email", pendingUserModel.getEmail());
                }
                pendingAdapter = new PendingAdapter(PendingUsersActivity.this, pendingList,
                        new PendingAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View v)
                    {
                        currentModel = pendingList.get(position);
                        Log.d("lognotes", currentModel.getEmail());
                        Log.d("lognotes", currentModel.getPassword());
                        Log.d("lognotes", currentModel.getKey());
                        showAlertDialog();
                    }
                });
                recyclerView.setAdapter(pendingAdapter);
                pendingAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    @Override
    public void onItemClick(int position, View v)
    {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
    }

    public void showAlertDialog()
    {
        new AlertDialog.Builder(this).setTitle("Warning")
                .setMessage("Are you want to Delete this User ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        deleteUserData();

                    }
                })
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

    private void deleteUserData()
    {
        final DatabaseReference myref = FirebaseDatabase.getInstance().getReference("company");
        myref.child("Users").child(key).removeValue();

        myref.child("fingerprint").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot shot : dataSnapshot.getChildren())
                {
                    FingerPrintModel model = shot.getValue(FingerPrintModel.class);
                    if (model.getId().equals(key))
                    {
                       String ref = shot.getRef().getKey();
                       myref.child("fingerprint").child(ref).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        pendingAdapter.notifyDataSetChanged();



        Intent intent = new Intent(PendingUsersActivity.this, PendingUsersActivity.class );
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PendingUsersActivity.this, CompanyHome.class );
        startActivity(intent);
        finish();
    }
}