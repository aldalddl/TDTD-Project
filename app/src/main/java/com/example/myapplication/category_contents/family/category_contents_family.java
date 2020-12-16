package com.example.myapplication.category_contents.family;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.CustomAdapter;
import com.example.myapplication.R;
import com.example.myapplication.community_detail;
import com.example.myapplication.model.category_user;
import com.example.myapplication.model.community_user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class category_contents_family extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManger;
    private ArrayList<community_user> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Context context;
    private String DestinationUid;
    //=================================gps


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fragment_category_contents_family, container, false);

        context = rootview.getContext();
        recyclerView = rootview.findViewById(R.id.recylerview); //아디 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        layoutManger = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManger);
        arrayList = new ArrayList<>(); //User 객체를 담을 어레이 리스트 (어댑터 쪽으로)
        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동

        databaseReference = database.getReference("community"); //파이어 베이스에서 user
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문으로 데이터 list를 추출해냄

                    community_user user1 = snapshot.getValue(community_user.class); //만들어뒀던 User 객체에 데이터를 담는
                    if(!user1.Cause1.contains("가족")) {
                            continue;
                        }

                    arrayList.add(user1); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        adapter = new CustomAdapter(arrayList, context, new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final Intent intent = new Intent(view.getContext(), community_detail.class);
                final String destinationUid = arrayList.get(position).uid;
                final String date1 = arrayList.get(position).date;

                final Bundle InfoBundle = new Bundle();
                DestinationUid = destinationUid;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문으로 데이터 list를 추출해냄
                            String uid = snapshot.child("uid").getValue(String.class); //uid
                            String date2 = snapshot.child("date").getValue(String.class); //uid

                            if (uid.equals(destinationUid)&& date1.equals(date2)) {
                                InfoBundle.putString("destinationUid", destinationUid);
                                InfoBundle.putString("date", date2);
                                InfoBundle.putString("writing", snapshot.child("writing").getValue(String.class));
                                intent.putExtras(InfoBundle);

                                startActivity(intent);
                                break;
                            } else {
                                continue;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


            }
        });
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결
        return rootview;
    }
}