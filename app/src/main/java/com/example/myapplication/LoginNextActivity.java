package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.report.report_menu;

public class LoginNextActivity extends AppCompatActivity {

    private TextView tv_result;
    private ImageView iv_profile;

    private Button goToMainButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_next);

        Intent intent = getIntent();
        String nickName = intent.getStringExtra("nickName");
        String photoUrl = intent.getStringExtra("photoUrl");

        tv_result = findViewById (R.id.tv_result);
        tv_result.setText(nickName);

        iv_profile  = findViewById (R.id.iv_profile);
        Glide.with(this).load(photoUrl).into(iv_profile);

        goToMainButton = (Button) findViewById(R.id.btn_gotomain);

        goToMainButton.setOnClickListener(new View.OnClickListener() { //If clicked plus
            @Override
            public void onClick(View v) { //Create a new intent which will send the record to the main
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent); //start activity
            }
        });
    }
}