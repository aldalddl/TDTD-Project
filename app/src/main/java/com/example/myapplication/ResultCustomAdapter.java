package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.face_user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class ResultCustomAdapter extends BaseAdapter {
    private String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private Face[] faces;
    private Context context;
    private LayoutInflater inflater;
    private Bitmap orig;

//    Context mContext = context.getApplicationContext();


    public ResultCustomAdapter(Face[] faces, Context context, Bitmap orig) {
        this.faces = faces;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.orig = orig;
    }


    @Override
    public int getCount() {
        return faces.length;
    }

    @Override
    public Object getItem(int position) {
        return faces[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(R.layout.listview_layout, null);
        }

        TextView age, gender, smile;

        ImageView imageView;

        age = view.findViewById(R.id.textAge);
        gender = view.findViewById(R.id.textGender);
        smile = view.findViewById(R.id.textSmile);

        imageView = view.findViewById(R.id.imgThumb);

        age.setText("나이: " + faces[position].faceAttributes.age);
        gender.setText("성별: " + faces[position].faceAttributes.gender);

        TreeMap<Double, String> treeMap = new TreeMap<>();
        treeMap.put(faces[position].faceAttributes.emotion.happiness, "행복");
        treeMap.put(faces[position].faceAttributes.emotion.anger, "화남");
        treeMap.put(faces[position].faceAttributes.emotion.disgust, "불안");
        treeMap.put(faces[position].faceAttributes.emotion.sadness, "슬픔");
        treeMap.put(faces[position].faceAttributes.emotion.neutral, "평범");
        treeMap.put(faces[position].faceAttributes.emotion.surprise, "놀람");
        treeMap.put(faces[position].faceAttributes.emotion.fear, "두려움");

        ArrayList<Double> arrayList = new ArrayList<>();
        TreeMap<Integer, String> rank = new TreeMap<>();

        int counter = 0;
        for (Map.Entry<Double, String> entry : treeMap.entrySet()) {
            String key = entry.getValue();
            Double value = entry.getKey();
            rank.put(counter, key);
            counter++;
            arrayList.add(value);
        } ;


        smile.setText(rank.get(rank.size() - 1) + ": " + 100 * arrayList.get(rank.size() - 1) + "% " + rank.get(rank.size() - 2) + ": " + 100 * arrayList.get(rank.size() - 2) + "%");
        face_user user = new face_user();
        user.sentiment = rank.get(rank.size() - 1);
        user.percent = String.valueOf(Math.floor(100 * arrayList.get(rank.size() - 1))) + "%";
        user.myuid = myUid;
        FirebaseDatabase.getInstance().getReference().child("face recognition").child(myUid).setValue(user);

        FaceRectangle faceRectangle = faces[position].faceRectangle;
        Bitmap bitmap = Bitmap.createBitmap(orig, faceRectangle.left, faceRectangle.top, faceRectangle.width, faceRectangle.height);

        if((Objects.equals(rank.get(rank.size() - 1), "슬픔") && 100 * arrayList.get(rank.size() - 1) > 10) ||
                (Objects.equals(rank.get(rank.size() - 1), "불안") && 100 * arrayList.get(rank.size() - 1) > 15) ||
                (Objects.equals(rank.get(rank.size() - 1), "두려움") && 100 * arrayList.get(rank.size() - 1) > 15)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("챗봇과 심리 상담이 필요해요").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Dialog", "확인");
                        Toast.makeText(context.getApplicationContext(), "확인", Toast.LENGTH_LONG).show();
                    }
                });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(context.getApplicationContext(),"담에 다시 만나요~~~~", Toast.LENGTH_LONG).show();

                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }



        imageView.setImageBitmap(bitmap);
        imageView.setImageBitmap(bitmap);
        return view;
    }
}



