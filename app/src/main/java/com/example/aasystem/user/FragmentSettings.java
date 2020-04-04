package com.example.aasystem.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aasystem.R;
import com.example.aasystem.auth.AuthActivity;
import com.example.aasystem.auth.HomeActivity;
import com.example.aasystem.auth.user.LoginUser;
import com.example.aasystem.utils.SharedPrefMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */

public class FragmentSettings extends Fragment
{
    private FirebaseAuth auth;
    private FirebaseUser muser;
    private String userid;
    ToggleButton togbtn;

    public FragmentSettings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_u_settings, container, false);

        final TextView Username = root.findViewById(R.id.tv_username);
        final TextView putuserid = root.findViewById(R.id.user_id);
        final TextView UserPhone = root.findViewById(R.id.tv_userphone);
        final TextView UserEmail = root.findViewById(R.id.tv_usermeail);

        final EditText putUsername = root.findViewById(R.id.et_username);
        final EditText putUserPhone = root.findViewById(R.id.et_userphone);
        final EditText putUserEmail = root.findViewById(R.id.et_useremail);

        final Button btnSave = root.findViewById(R.id.btnSave);
        btnSave.setVisibility(View.INVISIBLE);

        auth = FirebaseAuth.getInstance();
        muser = auth.getCurrentUser();
        userid = muser.getUid();

        DatabaseReference fir = FirebaseDatabase.getInstance().getReference("company");
        final DatabaseReference myref = fir.child("Users").child(userid);
        myref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Username.setText(String.valueOf(dataSnapshot.child("user_name").getValue()));
                putuserid.setText(String.valueOf(dataSnapshot.child("user_Id").getValue()));
                UserPhone.setText(String.valueOf(dataSnapshot.child("phone_Num").getValue()));
                UserEmail.setText(dataSnapshot.child("e_mail").getValue(String.class));

                putUsername.setText(String.valueOf(dataSnapshot.child("user_name").getValue()));
                putUserPhone.setText(String.valueOf(dataSnapshot.child("phone_Num").getValue()));
                putUserEmail.setText(String.valueOf(dataSnapshot.child("e_mail").getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       /* final DatabaseReference myref = fir.child("Users").child(userid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // هذا الكود لجلب البيانات من الداتا بيس
                Username.setText(String.valueOf(dataSnapshot.child("user_name").getValue()));
                putuserid.setText(String.valueOf(dataSnapshot.child("user_Id").getValue()));
                UserPhone.setText(String.valueOf(dataSnapshot.child("phone_Num").getValue()));
                UserEmail.setText(dataSnapshot.child("e_mail").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };*/
        /**Logout button*/
        Button logout=root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SharedPrefMethods sharedPrefMethods = new SharedPrefMethods(getActivity());
                sharedPrefMethods.deleteUserData();
                Intent i = new Intent(getActivity(), AuthActivity.class);
                startActivity(i);
            }
        });

      //  myref.addListenerForSingleValueEvent(valueEventListener);

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                togbtn.setChecked(false);

                putUsername.setVisibility(View.INVISIBLE);
                Username.setVisibility(View.VISIBLE);

                putUserPhone.setVisibility(View.INVISIBLE);
                UserPhone.setVisibility(View.VISIBLE);

                putUserEmail.setVisibility(View.INVISIBLE);
                UserEmail.setVisibility(View.VISIBLE);

                btnSave.setVisibility(View.INVISIBLE);

                // هذا الكود لتحديث البيانات في الداتا بيس
                String username = putUsername.getText().toString().trim();
                if(!username.isEmpty())
                {
                    myref.child("user_name").setValue(username);
                }
                String phone = putUserPhone.getText().toString().trim();
                if(!phone.isEmpty())
                {
                    myref.child("phone_Num").setValue(phone);
                }
                String email = putUserEmail.getText().toString().trim();
                if(!email.isEmpty())
                {
                    myref.child("e_mail").setValue(email);
                }
            }
        });


        /**Toggle Button*/
        togbtn=root.findViewById(R.id.togg);
        togbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    putUsername.setVisibility(View.VISIBLE);
                    Username.setVisibility(View.INVISIBLE);

                    putUserPhone.setVisibility(View.VISIBLE);
                    UserPhone.setVisibility(View.INVISIBLE);

                    putUserEmail.setVisibility(View.VISIBLE);
                    UserEmail.setVisibility(View.INVISIBLE);

                    btnSave.setVisibility(View.VISIBLE);
                }
                else
                {
                    putUsername.setVisibility(View.INVISIBLE);
                    Username.setVisibility(View.VISIBLE);

                    putUserPhone.setVisibility(View.INVISIBLE);
                    UserPhone.setVisibility(View.VISIBLE);

                    putUserEmail.setVisibility(View.INVISIBLE);
                    UserEmail.setVisibility(View.VISIBLE);

                    btnSave.setVisibility(View.INVISIBLE);
                }
            }
        });
        return root ;
    }
}