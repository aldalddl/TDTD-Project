package com.example.myapplication.report;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.reportFragment;

public class report_menu extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction;
    private com.example.myapplication.reportFragment reportFragment = new reportFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_menu);

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrame2, reportFragment).commitAllowingStateLoss();
    }



}