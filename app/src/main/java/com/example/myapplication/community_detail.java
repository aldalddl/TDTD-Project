package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.report.writingSolution;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class community_detail extends AppCompatActivity {
    private String destinationUid;
    private String destinationDate;
    private String destinationWriting;
    private FirebaseDatabase database; //파이어베이스 데이터베이스 연동
    private DatabaseReference databaseReference;
    private Bundle bundle;
    TextView target_writing;
    ImageView favorite_image;
    TextView favorite_count;
    Button target_report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);
        target_writing = findViewById(R.id.target_writing);
        favorite_image = findViewById(R.id.favorite_image);
        favorite_count = findViewById(R.id.favorite_count);
        target_report = findViewById(R.id.target_report);


        Intent intent = getIntent();
        destinationUid = intent.getStringExtra("destinationUid");
        destinationDate = intent.getStringExtra("date");
        destinationWriting = intent.getStringExtra("writing");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("anxiety information").child(destinationUid);

        target_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Intent intent2 = new Intent();
                            intent2 = new Intent(getApplicationContext(), TargetDetailReportActivity.class);

                            String uid = snapshot.child("date").getValue(String.class); //uid
                            if(destinationDate.equals(uid)) {
                                String num = snapshot.child("anxiety").getValue(String.class); //uid
                                String first = snapshot.child("first").getValue(String.class); //uid
                                String third = snapshot.child("third").getValue(String.class); //uid
                                String result_explain = snapshot.child("result_explain").getValue(String.class); //uid
                                intent2.putExtra("anxiety", num);
                                intent2.putExtra("first", first);
                                intent2.putExtra("third", third);
                                intent2.putExtra("result_explain", result_explain);
                                startActivity(intent2);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동

        databaseReference = database.getReference("anxiety information").child(destinationUid); //myUid

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.child("date").getValue(String.class); //uid
                    if(destinationDate.equals(uid)) {
                        target_writing.setText(destinationWriting);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



    }
}