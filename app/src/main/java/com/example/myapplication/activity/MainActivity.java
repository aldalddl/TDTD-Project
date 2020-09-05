package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.Toolbar;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.UserInfoFragment;
import com.example.myapplication.fragment.UserListFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends BasicActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbarTitle(getResources().getString(R.string.app_name));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Community");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.img_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                init();
                break;
        }
    }

    //Action Bar '내 정보'
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_btn1:
                UserInfoFragment userInfoFragment = new UserInfoFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, userInfoFragment)
                        .commit();
                return true;
//            case android.R.id.home:
//                finish();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            myStartActivity(SignUpActivity.class);
        } else {
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(firebaseUser.getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                                myStartActivity(MemberInitActivity.class);
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });

//            HomeFragment homeFragment = new HomeFragment();
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, homeFragment)
//                    .commit();

//            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    switch (item.getItemId()) {
//                        case R.id.home:
//                            HomeFragment homeFragment = new HomeFragment();
//                            getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.container, homeFragment)
//                                    .commit();
//                            return true;
//                        case R.id.myInfo:
//                            UserInfoFragment userInfoFragment = new UserInfoFragment();
//                            getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.container, userInfoFragment)
//                                    .commit();
//                            return true;
//                        case R.id.userList:
//                            UserListFragment userListFragment = new UserListFragment();
//                            getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.container, userListFragment)
//                                    .commit();
//                            return true;
//                    }
//                    return false;
//                }
//            });
        }
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 1);
    }

}