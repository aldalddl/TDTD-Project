package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction;

    private IoTFragment iotFragment = new IoTFragment();
    private CommunityFragment communityFragment = new CommunityFragment();
    private TDFragment tdFragment = new TDFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottomNav);

        // Set initial fragment of bottom navigation
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrame, tdFragment).commitAllowingStateLoss();

        // bottom navigation click action
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                transaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.navigation_community: {
                        transaction.replace(R.id.mainFrame, communityFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.navigation_TD: {
                        transaction.replace(R.id.mainFrame, tdFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.navigation_IoT: {
                        transaction.replace(R.id.mainFrame, iotFragment).commitAllowingStateLoss();
                        return true;
                    }
                }
                return false;
            }
        });     //[END] of OnNavigationItemSelectedListener
    }
}

