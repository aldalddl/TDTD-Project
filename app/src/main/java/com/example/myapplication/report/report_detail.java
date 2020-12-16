package com.example.myapplication.report;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class report_detail extends AppCompatActivity {
    private TextView report_detail_num;
    private TextView report_detail_cause;
    private TextView report_detail_conclusion;
    private TextView report_detail_colortherapy;
    private Button report_detail_button;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private String destinationUid;
    private Intent intent1;
    private Intent intent2;
    private Bundle bundle;
    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        report_detail_num = findViewById(R.id.report_detail_num);
        report_detail_cause = findViewById(R.id.report_detail_cause);
        report_detail_conclusion = findViewById(R.id.report_detail_conclusion);
        report_detail_colortherapy = findViewById(R.id.report_detail_colortherapy);
        report_detail_button = findViewById(R.id.report_detail_button);
        report_detail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 intent2 = new Intent(getApplicationContext(), writingSolution.class);
                bundle = new Bundle();
                bundle.putString("destinationUid", destinationUid);

                 intent2.putExtras(bundle);
                 startActivity(intent2);
            }
        });
        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("anxiety information").child(myUid); //myUid

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        report_detail_num.setText(snapshot.child("about_anxiety").getValue(String.class));
                        report_detail_cause.setText(snapshot.child("cause1").getValue(String.class));
                        report_detail_conclusion.setText(snapshot.child("cause2").getValue(String.class));
                        report_detail_colortherapy.setText(snapshot.child("result_explain").getValue(String.class));
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}