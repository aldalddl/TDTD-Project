package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.category_contents.family.category_contents_family;
import com.example.myapplication.category_contents.family.contents_family;
import com.example.myapplication.category_contents.gender.category_contents_gender;
import com.example.myapplication.category_contents.gender.contents_gender;
import com.example.myapplication.category_contents.myself.category_contents_myself;
import com.example.myapplication.category_contents.myself.contents_myself;
import com.example.myapplication.category_contents.relation.category_contents_relation;
import com.example.myapplication.category_contents.relation.contents_relation;

public class category_menu extends AppCompatActivity {
    ImageButton category_family;
    ImageButton category_gender;
    ImageButton category_relation;
    ImageButton category_myself;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_menu);

        category_family = findViewById(R.id.category_family);
        category_gender = findViewById(R.id.category_gender);
        category_relation = findViewById(R.id.category_relation);
        category_myself = findViewById(R.id.category_myself);

        category_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), contents_family.class);
                startActivity(intent);
            }
        });
        category_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), contents_gender.class);
                startActivity(intent);

            }
        });
        category_relation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), contents_relation.class);
                startActivity(intent);

            }
        });
        category_myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), contents_myself.class);
                startActivity(intent);

            }
        });
    }
}