package edu.neu.app;

import java.util.LinkedList;

public class HomeworkList {
    private int count;
    private LinkedList<Homework> homeworkList;

    HomeworkList() {
        homeworkList = new LinkedList<>();
    }

    public int getCount() {
        return count;
    }

    public LinkedList<Homework> getHomeworkList() {
        return homeworkList;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setHomeworkList(LinkedList<Homework> homeworkList) {
        this.homeworkList = homeworkList;
    }
}
