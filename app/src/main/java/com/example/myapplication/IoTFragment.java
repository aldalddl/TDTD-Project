package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.community_user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class IoTFragment extends Fragment {

    private static final String TAG = "bluetooth2";

    Button btnLed1, btnLed2, btnLed3, btnLedDark, btnLedBright, btnLedMiddleBright, btnLed4, btnLedOn, btnLedRecommend;
    TextView txtArduino;
    RelativeLayout rlayout;
    Handler h;

    final int RECIEVE_MESSAGE = 1;        // Status  for Handler
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();
    private static int flag = 0;

    private ConnectedThread mConnectedThread;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module (you must edit this line)
    private static String address = "98:D3:36:71:60:35";

    private Context context;
    private Context context2;


    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private String destinationUid;
    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String colortherapy;
    String colortherapy_result;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_iot, container, false);

        btnLed1 = (Button)view.findViewById(R.id.btnLed1);
        btnLed2 = (Button)view.findViewById(R.id.btnLed2);
        btnLed3 = (Button)view.findViewById(R.id.btnLed3);
        btnLedDark = (Button)view.findViewById(R.id.btnLedDark);
        btnLedBright = (Button)view.findViewById(R.id.btnLedBright);
        btnLed4 = (Button)view.findViewById(R.id.btnLed4);
        btnLedMiddleBright = (Button)view.findViewById(R.id.btnLedMiddleBright);
        btnLedOn = (Button)view.findViewById(R.id.btn_light_powerbtn_off);
        btnLedRecommend = (Button)view.findViewById(R.id.btnLedRecommend);


                database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("anxiety information");
//        databaseReference = database.getReference("anxiety information").child(myUid); //myUid

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.child("date").getValue(String.class); //uid
                    if(myUid.equals(uid)) {
                        colortherapy = snapshot.child("result_color").getValue(String.class).toString();
                        context = container.getContext();
//                        Toast.makeText(context, colortherapy, Toast.LENGTH_LONG).show();
//                        colortherapy_result = snapshot.child("result_explain").getValue(String.class).toString();
//                        context2 = container.getContext();
//                        Toast.makeText(context2, colortherapy_result, Toast.LENGTH_LONG).show();

//                        System.out.println("colortherapy 값 :");
//                        System.out.println(colortherapy);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("error");
            }
        });

        rlayout = (RelativeLayout)view.findViewById(R.id.layout);
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);
                        sb.append(strIncom);
                        int endOfLineIndex = sb.indexOf("\r\n");
                        if (endOfLineIndex > 0) {
                            String sbprint = sb.substring(0, endOfLineIndex);
                            sb.delete(0, sb.length());
                            txtArduino.setText("Data from Arduino: " + sbprint);
                            if(flag%4==3){
                                rlayout.setBackgroundColor(Color.rgb(255, 255, 255));
                            }
                            else if(flag%4==1){ //R
                                rlayout.setBackgroundColor(Color.rgb(255, 0, 0));
                            }
                            else if(flag%4==2){ //G
                                rlayout.setBackgroundColor(Color.rgb(0, 255, 0));
                            }
                            else if(flag%4==0){ //B
                                rlayout.setBackgroundColor(Color.rgb(0, 0, 255));
                            }
                            else if(flag%4==0){ //Dark
                                rlayout.setBackgroundColor(Color.rgb(0, 0, 255));
                            }
                            else if(flag%4==0){ //Bright
                                rlayout.setBackgroundColor(Color.rgb(0, 0, 255));
                            }
                            flag++;
                            btnLed1.setEnabled(true);
                            btnLed2.setEnabled(true);
                            btnLed3.setEnabled(true);
                            btnLed4.setEnabled(true);

                        }
                        break;
                }
            };
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        btnLed1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("1");
                //Toast.makeText(getBaseContext(), "Turn on First LED", Toast.LENGTH_SHORT).show();
            }
        });
        btnLed2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("2");
                //Toast.makeText(getBaseContext(), "Turn on Second LED", Toast.LENGTH_SHORT).show();
            }
        });
        btnLed3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("3");
                //Toast.makeText(getBaseContext(), "Turn on Third LED", Toast.LENGTH_SHORT).show();
            }
        });
        btnLedDark.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("4");
                //Toast.makeText(getBaseContext(), "Turn on Third LED", Toast.LENGTH_SHORT).show();
            }
        });
        btnLedBright.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("5");
                //Toast.makeText(getBaseContext(), "Turn on Third LED", Toast.LENGTH_SHORT).show();
            }
        });
        btnLedMiddleBright.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("6");
                //Toast.makeText(getBaseContext(), "Turn on Third LED", Toast.LENGTH_SHORT).show();
            }
        });
        btnLed4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("0");
                //Toast.makeText(getBaseContext(), "Turn on all LEDs", Toast.LENGTH_SHORT).show();
            }
        });

        btnLedRecommend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (colortherapy.equals("노랑색")){
                    btnLedRecommend.setSelected(true);
                    mConnectedThread.write("9");
                    Toast.makeText(context, "노랑색 불 켜짐", Toast.LENGTH_LONG).show();
                }
                else if (colortherapy.equals("보라색")){
                    mConnectedThread.write("0");
                    Toast.makeText(context, "보라색 불 켜짐", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLedRecommend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (colortherapy.equals("노랑색")){
//                    btnLedRecommend.setSelected(true);
                    mConnectedThread.write("9");
                    Toast.makeText(context, "노랑색 불 켜짐", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLedOn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        mConnectedThread.write("7");
                        btnLedOn.setBackgroundResource(R.drawable.img_iot_light_powerbtn_on);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        mConnectedThread.write("8");
                        btnLedOn.setBackgroundResource(R.drawable.img_iot_light_powerbtn_off);
                        break;
                    }
                }
                return true;
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_light:
//                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
//                startActivity(new Intent(getApplicationContext(), SpeakerFragment.class));
//                Toast.makeText(getApplicationContext(), "무", Toast.LENGTH_SHORT).show();
                System.out.println("Clip clicked!");
                break;
            case R.id.menu_speaker:
                System.out.println("Clip clicked!");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method  m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection",e);
            }
        }
        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "...onResume - try connect...");

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "...Connecting...");
        try {
            btSocket.connect();
            Log.d(TAG, "....Connection ok...");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d(TAG, "...Create Socket...");

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "...In onPause()...");

        try     {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }

    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter==null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private void errorExit(String title, String message){
//        Toast.makeText(getActivity().getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);        // Get number of bytes and message in "buffer"
                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();     // Send to message queue Handler
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String message) {
            Log.d(TAG, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
            }
        }
    }

}