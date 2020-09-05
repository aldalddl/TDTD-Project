package com.example.myapplication;

//import android.app.Activity;
//import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
//import android.bluetooth.BluetoothAdapter;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;

import com.example.myapplication.activity.MemberInitActivity;
import com.example.myapplication.activity.SignUpActivity;
import com.example.myapplication.fragment.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.appcompat.widget.Toolbar;
import com.example.myapplication.R;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.UserInfoFragment;

//import app.akexorcist.bluetotohspp.library.BluetoothSPP;
//import app.akexorcist.bluetotohspp.library.BluetoothState;
//import app.akexorcist.bluetotohspp.library.DeviceList;

public class MainActivity_original extends AppCompatActivity {
    private static final String TAG = "MainActivity";

//    private BluetoothSPP bt;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction;
//      if (parentFragment == null) {
//        fragmentManager.beginTransaction()
//                .add(R.id.container, IoTFragment.getInstance(), TAG_PARENT).commit();
//    }
    private HomeFragment homeFragment = new HomeFragment();
    private IoTFragment iotFragment = new IoTFragment();
    private CommunityFragment communityFragment = new CommunityFragment();
    private TDFragment tdFragment = new TDFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_original);

        init();

//        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottomNav);
//
//        // Set initial fragment of bottom navigation
//        transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.mainFrame, tdFragment).commitAllowingStateLoss();
//
//        // bottom navigation click action
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//
//                transaction = fragmentManager.beginTransaction();
//                switch (menuItem.getItemId()) {
//                    case R.id.navigation_community: {
//                        transaction.replace(R.id.mainFrame, communityFragment).commitAllowingStateLoss();
//                        return true;
//                    }
//                    case R.id.navigation_TD: {
//                        transaction.replace(R.id.mainFrame, tdFragment).commitAllowingStateLoss();
//                        return true;
//                    }
//                    case R.id.navigation_IoT: {
//                        transaction.replace(R.id.mainFrame, iotFragment).commitAllowingStateLoss();
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });     //[END] of OnNavigationItemSelectedListener




//        //------블루투스 연결 부분------//
//        bt = new BluetoothSPP(this); //Initializing
//
//        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
//            Toast.makeText(getApplicationContext()
//                    , "Bluetooth is not available"
//                    , Toast.LENGTH_SHORT).show();
//            finish();
//        }
//
//        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
//            public void onDataReceived(byte[] data, String message) {
//                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
//            public void onDeviceConnected(String name, String address) {
//                Toast.makeText(getApplicationContext()
//                        , "Connected to " + name + "\n" + address
//                        , Toast.LENGTH_SHORT).show();
//            }
//
//            public void onDeviceDisconnected() { //연결해제
//                Toast.makeText(getApplicationContext()
//                        , "Connection lost", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onDeviceConnectionFailed() { //연결실패
//                Toast.makeText(getApplicationContext()
//                        , "Unable to connect", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Button btnConnect = findViewById(R.id.btn_light_powerbtn_off); //연결시도
//        btnConnect.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
//                    bt.disconnect();
//                } else {
//                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
//                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
//                }
//            }
//        });
//        //[END] of 블루투스 부분
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

            BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottomNav);

            // Set initial fragment of bottom navigation
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.mainFrame, tdFragment).commitAllowingStateLoss();

            // bottom navigation click action
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    transaction = fragmentManager.beginTransaction();
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_community: {
                            transaction.replace(R.id.mainFrame, homeFragment).commitAllowingStateLoss();
                            return true;
                        }
                        case R.id.navigation_TD: {
                            transaction.replace(R.id.mainFrame, tdFragment).commitAllowingStateLoss();
                            return true;
                        }
                        case R.id.navigation_IoT: {
                            transaction.replace(R.id.mainFrame, iotFragment).commitAllowingStateLoss();
                            return true;
                        }
                    }
                    return false;
                }
            });     //[END] of OnNavigationItemSelectedListener

//            HomeFragment homeFragment = new HomeFragment();
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, homeFragment)
//                    .commit();
//
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

//    public void onDestroy() {
//        super.onDestroy();
//        bt.stopService(); //블루투스 중지
//    }

//    public void onStart() {
//        super.onStart();
//        if (!bt.isBluetoothEnabled()) { //
//            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
//        } else {
//            if (!bt.isServiceAvailable()) {
//                bt.setupService();
//                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
//                setup();
//            }
//        }
//    }

//    public void setup() {
//        Button btnSend = findViewById(R.id.btn_bluetooth_send); //데이터 전송
//        btnSend.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                bt.send("Text", true);
//            }
//        });
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
//            if (resultCode == Activity.RESULT_OK)
//                bt.connect(data);
//        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
//            if (resultCode == Activity.RESULT_OK) {
//                bt.setupService();
//                bt.startService(BluetoothState.DEVICE_OTHER);
//                setup();
//            } else {
//                Toast.makeText(getApplicationContext()
//                        , "Bluetooth was not enabled."
//                        , Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
//    }

}




