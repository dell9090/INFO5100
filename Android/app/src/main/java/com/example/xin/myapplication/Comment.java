package com.example.xin.myapplication;

import java.util.Date;

public class Comment {

    private int id;
    private int usrid;//usrid
    private int subjectid;
    private int post_type;
    private int post_id; //pdf_id
    private int page;
    private int top;
    private String content;
    private int agreecount;
    private int ifagree;

    public Comment() {

    }

    public Comment(int usrid, int subjectid, int post_type, int post_id, int page,
                   int top, String content, int agreecount, int ifagree) {
        this.usrid = usrid;
        this.subjectid = subjectid;
        this.post_type = post_type;
        this.page = page;
        this.top = top;
        this.content = content;
        this.agreecount = agreecount;
        this.ifagree = ifagree;
    }

    public int getId() {
        return id;
    }

    public int getAgreecount() {
        return agreecount;
    }

    public int getPage() {
        return page;
    }

    public int getPost_id() {
        return post_id;
    }

    public int getIfagree() {
        return ifagree;
    }

    public int getPost_type() {
        return post_type;
    }

    public int getSubjectid() {
        return subjectid;
    }

    public int getTop() {
        return top;
    }

    public int getUsrid() {
        return usrid;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAgreecount(int agreecount) {
        this.agreecount = agreecount;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIfagree(int ifagree) {
        this.ifagree = ifagree;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public void setPost_type(int post_type) {
        this.post_type = post_type;
    }

    public void setSubjectid(int subjectid) {
        this.subjectid = subjectid;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setUsrid(int usrid) {
        this.usrid = usrid;
    }
}
