package com.example.xin.myapplication;

import android.util.Log;

import java.sql.Timestamp;
import java.sql.Time;


public class Notification {
    private String title;
    private String content;
    private String updated_at;

    public String getTitle() {
        return title;
    }

    public String getDate() {
        System.out.println("time:"+updated_at);
        return updated_at;
    }

    public String getContent() {
        return content;
    }
}
