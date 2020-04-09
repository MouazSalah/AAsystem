package com.example.aasystem.user.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.aasystem.R;
import com.example.aasystem.user.fragment.FragmentHome;
import com.example.aasystem.user.fragment.FragmentRecord;
import com.example.aasystem.user.fragment.FragmentSettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserHome extends AppCompatActivity
{
    BottomNavigationView navBar;
    FrameLayout mainFrame;
    FragmentHome homeFragment;
    FragmentRecord recordsFragment;
    FragmentSettings settingsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);

        /**Nav and frame */
        mainFrame = findViewById(R.id.main_frame);
        navBar = findViewById(R.id.main_nav);

        /**Fragments constructors*/

        homeFragment= new FragmentHome();
        recordsFragment= new FragmentRecord();
        settingsFragment = new FragmentSettings();

        /**default fragment*/

        setFrag(homeFragment);

        /**Select listener*/
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {

                    case R.id.home:
                        setFrag(homeFragment);
                        return true;

                    case R.id.records:
                        setFrag(recordsFragment);
                        return true;

                    case R.id.settings:
                        setFrag(settingsFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }
    private void setFrag(Fragment Fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, Fragment );
        fragmentTransaction.commit();
    }
}
