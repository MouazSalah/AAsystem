package com.example.aasystem.company;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.aasystem.R;
import com.example.aasystem.auth.AuthActivity;
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
public class SettingsFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference myRef;
    private FirebaseUser muser;
    private String userid;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_settings, container, false);

        final TextView username = root.findViewById(R.id.username);
        final TextView Password = root.findViewById(R.id.Password);
        final TextView Email = root.findViewById(R.id.Email);
        final TextView Orgname = root.findViewById(R.id.Orgname);
        final TextView from = root.findViewById(R.id.from);
        final TextView To = root.findViewById(R.id.To);

        final EditText etUsername = root.findViewById(R.id.etUsername);
        final EditText etRegPassword = root.findViewById(R.id.etRegPassword);
        final EditText etRegEmail = root.findViewById(R.id.etRegEmail);
        final EditText etOrgname = root.findViewById(R.id.etOrgname);
        final EditText from_hour_edit = root.findViewById(R.id.from_hour_edit);
        final EditText To_hour_edit = root.findViewById(R.id.To_hour_edit);

        Button btnSave = root.findViewById(R.id.btnSave);
        /**Logout button*/
        Button logout=root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefMethods sharedPrefMethods = new SharedPrefMethods(getActivity());
                sharedPrefMethods.deleteUserData();
                Intent i = new Intent(getActivity(), AuthActivity.class);
                startActivity(i);
            }
        });


        auth = FirebaseAuth.getInstance();
        muser = auth.getCurrentUser();
        userid = muser.getUid();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    myRef = FirebaseDatabase.getInstance().getReference("company");
                    myRef.child("ComAc").child(userid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
// هذا الكود لعرض البيانات من الداتا بيس
                                username.setText(String.valueOf(dataSnapshot.child("Username").getValue()));
                                Password.setText(String.valueOf(dataSnapshot.child("password").getValue()));
                                Email.setText(String.valueOf(dataSnapshot.child("email").getValue()));
                                Orgname.setText(String.valueOf(dataSnapshot.child("CompanyName").getValue()));
                                from.setText(String.valueOf(dataSnapshot.child("From").getValue()));
                                To.setText(String.valueOf(dataSnapshot.child("To").getValue()));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        };

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Updatae user data in the database
                String username = etUsername.getText().toString().trim();
                if(!username.isEmpty()){
                    myRef.child("ComAc").child(userid).child("Username").setValue(username);
                }

                String pass = etRegPassword.getText().toString().trim();
                if(!pass.isEmpty()) {
                    myRef.child("ComAc").child(userid).child("password").setValue(pass);
                }

                String email = etRegEmail.getText().toString().trim();
                if(!email.isEmpty()) {
                    myRef.child("ComAc").child(userid).child("email").setValue(email);
                }

                String comp = etOrgname.getText().toString().trim();
                if(!comp.isEmpty()) {
                    myRef.child("ComAc").child(userid).child("CompanyName").setValue(comp);
                }

                String from = from_hour_edit.getText().toString().trim();
                if(!from.isEmpty()) {
                    myRef.child("ComAc").child(userid).child("From").setValue(from);
                }

                String to = To_hour_edit.getText().toString().trim();
                if(!to.isEmpty()){
                    myRef.child("ComAc").child(userid).child("To").setValue(to);
                }
            }
        });


        /**Toggle Button*/
        ToggleButton togbtn=root.findViewById(R.id.togg);
        togbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    etUsername.setVisibility(View.VISIBLE);
                    username.setVisibility(View.INVISIBLE);

                    etRegPassword.setVisibility(View.VISIBLE);
                    Password.setVisibility(View.INVISIBLE);

                    etRegEmail.setVisibility(View.VISIBLE);
                    Email.setVisibility(View.INVISIBLE);

                    etOrgname.setVisibility(View.VISIBLE);
                    Orgname.setVisibility(View.INVISIBLE);

                    from_hour_edit.setVisibility(View.VISIBLE);
                    from.setVisibility(View.INVISIBLE);

                    To_hour_edit.setVisibility(View.VISIBLE);
                    To.setVisibility(View.INVISIBLE);

                }
                else
                {
                    etUsername.setVisibility(View.INVISIBLE);
                    username.setVisibility(View.VISIBLE);

                    etRegPassword.setVisibility(View.INVISIBLE);
                    Password.setVisibility(View.VISIBLE);

                    etRegEmail.setVisibility(View.INVISIBLE);
                    Email.setVisibility(View.VISIBLE);


                    etOrgname.setVisibility(View.INVISIBLE);
                    Orgname.setVisibility(View.VISIBLE);

                    from_hour_edit.setVisibility(View.INVISIBLE);
                    from.setVisibility(View.VISIBLE);

                    To_hour_edit.setVisibility(View.INVISIBLE);
                    To.setVisibility(View.VISIBLE);


                }

            }
        });

        return root ;

    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }


}
