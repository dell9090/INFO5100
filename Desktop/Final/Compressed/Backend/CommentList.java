package edu.neu.app;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CommentList {
    private int count;
    private List<Comment> comments;

    CommentList() {
        comments = new ArrayList<>();
    }

    public int getCount() {
        return count;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
