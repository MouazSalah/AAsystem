package com.example.aasystem.company;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.aasystem.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CompanyHome extends AppCompatActivity {

    BottomNavigationView navBar;
    FrameLayout mainFrame;
    HomeFragment homeFragment;
    RecordsFragment recordsFragment;
    SettingsFragment settingsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_home);

        /**Home fragments*/


        /**Nav and frame */
        mainFrame = findViewById(R.id.main_frame);
        navBar = findViewById(R.id.main_nav);

        /**Fragments constructors*/

        homeFragment = new HomeFragment();
        recordsFragment = new RecordsFragment();
        settingsFragment = new SettingsFragment();

        /**default fragment*/
        setFragment(homeFragment);


        /**Select listener*/
        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.home:
                        setFragment(homeFragment);
                        return true;

                    case R.id.records:
                        setFragment(recordsFragment);
                        return true;

                    case R.id.settings:
                        setFragment(settingsFragment);
                        return true;


                    default:
                        return false;
                }


            }


        });
    }
           private void setFragment(Fragment Fragment) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_frame, Fragment );
                     fragmentTransaction.commit();
                }

















}
