package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TargetDetailReportActivity extends AppCompatActivity {
    private TextView target_report_detail_num;
    private TextView target_report_detail_cause;
    private TextView target_report_detail_conclusion;
    private TextView target_report_detail_colortherapy;
    private Intent intent1;

    private String destinationNum;
    private String destinationFirst;
    private String destinationThird;
    private String destinationResultExplain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_detail_report);

        intent1 = getIntent();

        destinationNum = intent1.getStringExtra("anxiety");
        destinationFirst = intent1.getStringExtra("first");
        destinationThird = intent1.getStringExtra("third");
        destinationResultExplain = intent1.getStringExtra("result_explain");

        Toast.makeText(this, destinationNum, Toast.LENGTH_SHORT).show();
        target_report_detail_num = findViewById(R.id.target_report_detail_num);
        target_report_detail_cause = findViewById(R.id.target_report_detail_cause);
        target_report_detail_conclusion = findViewById(R.id.target_report_detail_conclusion);
        target_report_detail_colortherapy = findViewById(R.id.target_report_detail_colortherapy);

        target_report_detail_num.setText(destinationNum);
        target_report_detail_cause.setText(destinationFirst);
        target_report_detail_conclusion.setText(destinationThird);
        target_report_detail_colortherapy.setText(destinationResultExplain);


    }
}