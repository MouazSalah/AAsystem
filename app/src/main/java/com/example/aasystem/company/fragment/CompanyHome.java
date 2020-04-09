package com.example.aasystem.company.fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.aasystem.R;
import com.example.aasystem.company.fragment.HomeFragment;
import com.example.aasystem.company.fragment.RecordsFragment;
import com.example.aasystem.company.fragment.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CompanyHome extends AppCompatActivity
{
    BottomNavigationView navBar;
    FrameLayout mainFrame;
    HomeFragment homeFragment;
    RecordsFragment recordsFragment;
    SettingsFragment settingsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_home);

        mainFrame = findViewById(R.id.main_frame);
        navBar = findViewById(R.id.main_nav);


        setFragment(new HomeFragment());

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        homeFragment = new HomeFragment();
                        setFragment(homeFragment);
                        return true;

                    case R.id.records:
                         recordsFragment = new RecordsFragment();
                         setFragment(recordsFragment);
                        return true;

                    case R.id.settings:
                         settingsFragment = new SettingsFragment();
                         setFragment(settingsFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment Fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, Fragment );
        fragmentTransaction.commit();
    }

}
