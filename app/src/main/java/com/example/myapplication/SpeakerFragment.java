package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SpeakerFragment extends Fragment {

    private static final String ARG_NO = "ARG_NO";

    public static SpeakerFragment getInstance(int no) {

        SpeakerFragment fragment = new SpeakerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_NO, no);
        fragment.setArguments(bundle);
        return fragment;
    }

    public SpeakerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_speaker, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        TextView textView = view.findViewById(R.id.text3);
        int no = getArguments().getInt(ARG_NO, 0);
//        String text = "" + no + " 2번째 자식 프래그먼트";
//        textView.setText(text);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ChildFragment", "onDestroy");
    }
}
