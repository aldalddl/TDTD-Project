package com.example.myapplication.category_contents.family;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.myapplication.R;

public class contents_family extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction transaction;
    category_contents_family m = new category_contents_family();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_family);

               transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrame10, m).commitAllowingStateLoss();


    }
}