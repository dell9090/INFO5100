package edu.neu.app;

public class Response {
    private boolean status;
    private String name;
    private int userid;

    Response(){
        this.status = false;
        this.name = null;
        this.userid = -1;
    }

    Response(boolean status) {
        this.status = status;
    }

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

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserid() {
        return userid;
    }
}
