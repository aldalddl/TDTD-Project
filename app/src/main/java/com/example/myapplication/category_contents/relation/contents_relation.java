package com.example.myapplication.category_contents.relation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.category_contents.myself.category_contents_myself;

public class contents_relation extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction transaction;
    category_contents_relation m = new category_contents_relation();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_relation);

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrame13, m).commitAllowingStateLoss();


    }
}