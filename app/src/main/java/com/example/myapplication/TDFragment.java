package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class TDFragment extends Fragment {
    private Button btn_gototd;
    private Intent intent;
    private TextView tv_face_index;
    private TextView tv_anxiety_index;
    private FirebaseDatabase database; //파이어베이스 데이터베이스 연동
    private DatabaseReference databaseReference;
    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_td, container, false);
        tv_face_index = rootview.findViewById(R.id.tv_face_index);
        tv_anxiety_index = rootview.findViewById(R.id.tv_anxiety_index);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("face recognition");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Intent intent2 = new Intent();
                    intent2 = new Intent(getActivity(), TargetDetailReportActivity.class);

                    String uid = snapshot.child("myuid").getValue(String.class);

                    if(myUid.equals(uid)) {

                        String sentiment = snapshot.child("sentiment").getValue(String.class);
                        String percent = snapshot.child("percent").getValue(String.class);

                        tv_face_index.setText(sentiment);
                        tv_anxiety_index.setText(percent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btn_gototd = rootview.findViewById(R.id.btn_gototd);
        btn_gototd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), FaceRecognitionAcitivity.class);
                startActivity(intent);
            }
        });



        return rootview;
    }
}
