package com.example.xin.myapplication;

import android.util.Log;

public class LoginReceive {
    private boolean status;
    private String name;
    private int userid;
    private String password;
    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public int getUserid() {
        return userid;
    }

    //    private String token ;
//    private String message;
//    private int status_code;
//    private int admin;
//    private String name;
//    private int subjectid;
//    private String subjectname;
//    private int id;
//
//    public int getId() {
//        return id;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public int getStatus_code() {
//        return status_code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public int getAdmin() {
//        return admin;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getSubjectid() {
//        return subjectid;
//    }
//
//    public String getSubjectname() {
//        return subjectname;
//    }
//
//    //定义 输出返回数据 的方法
//    public void show() {
//        Log.d("test","token:"+token);
//        Log.d("test","message:"+message);
//        Log.d("test","status_code:"+status_code);
//    }
}