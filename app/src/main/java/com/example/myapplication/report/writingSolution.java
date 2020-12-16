package com.example.myapplication.report;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.model.community_user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class writingSolution extends AppCompatActivity {
    private EditText solution_write;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private Intent intent1;
    private String destinationUid;
    private ImageButton button;
    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String date = "20201212023000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        solution_write = findViewById(R.id.solution_writing);
        button = findViewById(R.id.solution_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                community_user user = new community_user();
                user.writing = solution_write.getText().toString();
                user.uid = myUid;
                user.date = date;
                user.recommendCount = 0;
                FirebaseDatabase.getInstance().getReference().child("community")
                        .child("0"+myUid + date).setValue(user);
                finish();
            }
        });



    }
}