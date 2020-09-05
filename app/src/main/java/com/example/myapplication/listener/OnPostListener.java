package com.example.myapplication.listener;

import com.example.myapplication.PostInfo;

public interface OnPostListener {
    void onDelete(PostInfo postInfo);
    void onModify();
}
