package com.example.myapplication.category_contents.myself;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.category_contents.family.category_contents_family;

public class contents_myself extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction transaction;
    category_contents_myself m = new category_contents_myself();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_myself);

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrame12, m).commitAllowingStateLoss();


    }
}