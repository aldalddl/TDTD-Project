package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication.report.report_menu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction;
    private community community = new community();
    private TDFragment tdFragment = new TDFragment();
    private IoTFragment ioTFragment = new IoTFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottomNav);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrame, community).commitAllowingStateLoss();

        // bottom navigation click action
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                transaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.navigation_community: {
                        transaction.replace(R.id.mainFrame, community).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.navigation_TD: {
                        transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                                .replace(R.id.mainFrame, tdFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.navigation_IoT: {
                        transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                                .replace(R.id.mainFrame, ioTFragment).commitAllowingStateLoss();
                        return true;
                    }
                }
                return false;
            }
        });     //[END] of OnNavigationItemSelectedListener


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.category_menu:
                intent = new Intent(getApplicationContext(), category_menu.class);
                startActivity(intent);
                return true;
            case R.id.report_menu:
                intent = new Intent(getApplicationContext(), report_menu.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}