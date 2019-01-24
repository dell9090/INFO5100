package edu.neu.app;

import java.util.LinkedList;
import java.util.List;

public class NotificationList {
    private int count;
    private LinkedList<Notification> notificationList;

    NotificationList(){
        notificationList = new LinkedList<Notification>();
    }

    public int getCount() {
        return count;
    }

    public LinkedList<Notification> getNotificationList() {
        return this.notificationList;
    }


    public void setCount(int count) {
        this.count = count;
    }

    public void setNotificationList(LinkedList<Notification> notificationList) {
        this.notificationList = notificationList;
    }
}
