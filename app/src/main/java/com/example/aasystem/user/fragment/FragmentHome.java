package com.example.aasystem.user.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aasystem.FingerPrintModel;
import com.example.aasystem.R;
import com.example.aasystem.user.activities.FingerPrintActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class FragmentHome extends Fragment
{
    private FirebaseAuth auth;
    private FirebaseUser muser;
    private String userid, secondCheck, userStatus;


    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View root = inflater.inflate(R.layout.fragment_u_home, container, false);

        TextView bring_date = root.findViewById(R.id.bring_date);
        final TextView att_time =  root.findViewById(R.id.att_time);
        final Button status = root.findViewById(R.id.A_status);
        final Button finger = root.findViewById(R.id.gotofinger);
        final TextView lev_time = root.findViewById(R.id.lev_time);
        final TextView checktxt = root.findViewById(R.id.checktxt);

        DateFormat formatt = new SimpleDateFormat("yyyy/MM/dd");
        long de = System.currentTimeMillis();
        final String date = formatt.format(new Date(de));

        bring_date.setText(date);

        auth = FirebaseAuth.getInstance();
        muser = auth.getCurrentUser();
        userid = muser.getUid();

        Calendar instance = Calendar.getInstance();
        int month = instance.get(Calendar.MONTH) + 1;
        int year = instance.get(Calendar.YEAR);
        int day = instance.get(Calendar.DAY_OF_MONTH);
        final String keyChild = year + "" + month + "" + day;

        final DatabaseReference firebase = FirebaseDatabase.getInstance().getReference("company");
        firebase.child("Users").child(userid).child("fingerprint").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.hasChild(keyChild))
                {
                    FingerPrintModel model = dataSnapshot.child(keyChild).getValue(FingerPrintModel.class);
                    att_time.setText(model.getAttend());
                    lev_time.setText(model.getLeave());
                    userStatus = model.getStatus();
                    secondCheck = model.getSecond_check();
                    checktxt.setText(model.getSecond_check());
                }
                else
                {
                    userStatus = "absent";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userStatus.equals("present"))
                {
                    Dialog builder = new Dialog (getContext());
                    builder.setContentView(R.layout.on_time);
                    builder.create();
                    builder.show();
                }
                else if (userStatus.equals("late"))
                {
                    Dialog builder = new Dialog (getContext());
                    builder.setContentView(R.layout.ur_late);
                    builder.create();
                    builder.show();
                }
                else
                {
                    Dialog builder = new Dialog (getContext());
                    builder.setContentView(R.layout.ur_absent);
                    builder.create();
                    builder.show();
                }
            }
        });



    /*    ValueEventListener valueEventListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

           //  from = String.valueOf(dataSnapshot.child("ComAc").child(userid).child("From").getValue());
           //  to = String.valueOf(dataSnapshot.child("ComAc").child(userid).child("To").getValue());
           //  onTime = String.valueOf(dataSnapshot.child("ComAc").child(userid).child("OnTime").getValue());

           //  attTim = String.valueOf(dataSnapshot.child("Users").child(userid).child("figerPrint").child(date).child("Time").getValue());
            // check = String.valueOf(dataSnapshot.child("Users").child(userid).child("figerPrint").child(date).child("Check").getValue());

           // att_time.setText(attTim);
           // checktxt.setText(check);
            status.setOnClickListener(new View.OnClickListener ()
            {
                @Override
                public void onClick(View root)
                {
                    if (secondCheck.equals("50%"))
                    {
                        Dialog builder = new Dialog (getContext());
                        builder.setContentView(R.layout.on_time);
                        builder.create();
                        builder.show();
                    }
                    else if (userStatus.equals("late"))
                    {
                        Dialog builder = new Dialog (getContext());
                        builder.setContentView(R.layout.ur_late);
                        builder.create();
                        builder.show();
                    }
                    else
                    {
                        Dialog builder = new Dialog (getContext());
                        builder.setContentView(R.layout.ur_absent);
                        builder.create();
                        builder.show();
                    }

                  *//*  Date time_att = parseDate(attTim);

                    Date time_from = parseDate(from);
                    Date time_on = parseDate(onTime);
                    Date time_to = parseDate(to);

                    if (time_from.before(time_att) && time_on.after(time_att))
                    {
                        Dialog builder = new Dialog (getContext());
                        builder.setContentView(R.layout.on_time);
                        builder.create();
                        builder.show();
                    }
                    else if (time_on.before(time_att) && time_to.after(time_att))
                    {
                        Dialog builder = new Dialog (getContext());
                        builder.setContentView(R.layout.ur_late);
                        builder.create();
                        builder.show();
                    }
                    else
                    {
                        Dialog builder = new Dialog (getContext());
                        builder.setContentView(R.layout.ur_absent);
                        builder.create();
                        builder.show();
                    }*//*
                }
            });

            //--------- for fingerprint button hiding
           // att_time.setText(attTim);
           // checktxt.setText(check);
         *//*   finger.setOnClickListener(new View.OnClickListener ()
            {
                @Override
                public void onClick(View root)
                {
                    Date time_att = parseDate(attTim);

                    Date time_from = parseDate(from);
                    Date time_on = parseDate(onTime);
                    Date time_to = parseDate(to);

                    if (time_from.before(time_att) && time_on.after(time_att))
                    {
                        finger.setVisibility(View.VISIBLE);
                    }
                    else if (time_on.before(time_att) && time_to.after(time_att))
                    {
                        finger.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        finger.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "you didn't attend yet!", Toast.LENGTH_SHORT).show();
                    }
                }
            });*//*

           *//*  leev =  String.valueOf(dataSnapshot.child("Users").child(userid).child("figerPrint").child(date).child("leaveTime").getValue());
             lev_time.setText(leev);*//*
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {

        }
    };

        firebase.addListenerForSingleValueEvent(valueEventListener);*/

        Button second = root.findViewById(R.id.second);
        second.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               firebase.child("Users").child(userid).child("fingerprint").addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                   {
                       if (dataSnapshot.hasChild(keyChild))
                       {
                          Intent intent = new Intent(getActivity(), FingerPrintActivity.class);
                          intent.putExtra("check", "second");
                           startActivity(intent);
                           getActivity().finish();
                       }
                       else
                       {
                           Toast.makeText(getActivity(), "You didn't first check", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(getActivity(), FingerPrintActivity.class);
                           intent.putExtra("check", "first");
                           startActivity(intent);
                           getActivity().finish();
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });

               // firebase.child("Users").child(userid).child("figerPrint").child(key).child("second_check").setValue(plus);
                // fir.child("Users").child(userid).child("figerPrint").child(date).child("Check").setValue(plus);
            }
        });


        /**go to finger page Button*/
        finger.setOnClickListener(new View.OnClickListener ()
        {
            public void onClick(View root)
            {
                Intent intent = new Intent(getActivity(), FingerPrintActivity.class);
                intent.putExtra("check", "first");
                startActivity(intent);
                getActivity().finish();
                /*
                Intent intent = new Intent(getActivity(), user_map.class);
                startActivity(intent);
            */}
        });
        return root ;
    }

    private Date parseDate(String date)
    {
        final String inputFormat = "HH:mm";
        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, new Locale("ar" , "SA"));

        try
        {
            return inputParser.parse(date);
        }
        catch (ParseException e)
        {
            return new Date(0);
        }
    }
}
