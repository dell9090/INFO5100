package com.example.xin.myapplication;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CommentReceive {
    private List<Comment> comments;
    private int count;

    public CommentReceive(){
        comments = new ArrayList<Comment>();
    }

    public int getCount() {
        return count;
    }

    public List<Comment> getCommentlist() {
        return comments;
    }

    public void setCommentlist(List<Comment> commentlist) {
        this.comments = commentlist;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
