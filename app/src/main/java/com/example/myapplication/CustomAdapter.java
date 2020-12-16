package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.model.community_user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;
    private ArrayList<community_user> arrayList; //아까 만든 class에서의 User
    private Context context;

    public CustomAdapter(ArrayList<community_user> arrayList, Context context, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item1, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    } //adapter에 연결이 되고난 후 viewholder를 최초로 만들어 낸다.

    @Override
    public void onBindViewHolder(@NonNull final CustomAdapter.CustomViewHolder holder, final int position) {
        final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        holder.content_textView.setText(arrayList.get(position).writing);
        holder.content_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });
        isLikes(arrayList.get(position).uid, arrayList.get(position).date ,holder.favorite_imageView);
        nrLikes(holder.favorite_textView, arrayList.get(position).uid, arrayList.get(position).date);

        holder.favorite_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.favorite_imageView.getTag().equals("recommend")) {
                    FirebaseDatabase.getInstance().getReference().child("recommend").child(arrayList.get(position).uid)
                            .child(arrayList.get(position).date).child(myUid).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("recommend").child(arrayList.get(position).uid)
                            .child(arrayList.get(position).date).child(myUid).removeValue();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder { //listview에서 만든 것들을 여기로 불러 놓을 거임
        ImageView content_imageButton;
        TextView content_textView;
        ImageView favorite_imageView;
        TextView favorite_textView;



        public CustomViewHolder(@NonNull View itemView) {
            super(itemView); //view 에서 상속을 받았기 때문에 itemView에서 찾아야한다
            this.content_imageButton = itemView.findViewById(R.id.backImage);
            this.content_textView = itemView.findViewById(R.id.contents);
            this.favorite_imageView = itemView.findViewById(R.id.favoriteImg);
            this.favorite_textView = itemView.findViewById(R.id.likeCount);
          }
    }

    public void isLikes(String postid, String postdate, final ImageView imageView) {
        //final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("recommend").child(postid).child(postdate);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(myUid).exists()) {
                    imageView.setImageResource(R.drawable.like_after);
                    imageView.setTag("recommended");
                } else {
                    imageView.setImageResource(R.drawable.btn_like);
                    imageView.setTag("recommend");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void nrLikes(final TextView recommend, String postid, String postdate) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("recommend").child(postid).child(postdate);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recommend.setText( dataSnapshot.getChildrenCount() + "");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
