package com.example.myapplication.category_contents.gender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.category_contents.family.category_contents_family;

public class contents_gender extends AppCompatActivity {
      private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction transaction;
    category_contents_gender m = new category_contents_gender();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_gender);

              transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFrame11, m).commitAllowingStateLoss();



    }
}