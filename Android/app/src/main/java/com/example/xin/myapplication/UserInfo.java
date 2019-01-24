package com.example.xin.myapplication;

import android.app.Application;

public class UserInfo extends Application {
    private int userid;//id
    private String userName;
    private int subjectid;
    private String ip;
    private int logined;
    private String subjectname;



    public String getUserName() {
        return userName;
    }

    public int getUserid() {
        return userid;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public String getIp() {
        return ip;
    }

    public int getSubjectid() {
        return subjectid;
    }

    public int getLogined() {
        return logined;
    }

    public void setLogined(int logined) {
        this.logined = logined;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setSubjectid(int subjectid) {
        this.subjectid = subjectid;
    }
}
