package com.example.xin.myapplication;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class NotificationReceive {
    private int count;
    private LinkedList<Notification> notificationList;

    NotificationReceive(){
        notificationList = new LinkedList<Notification>();
    }


    public int getCount() {
        return count;
    }

    public LinkedList<Notification> getNotificationList() {
        Log.d("test","in receive the date is "+notificationList.get(0).getDate());
        return notificationList;
    }
}
