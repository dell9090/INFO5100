package com.example.xin.myapplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CoursewareReceive {
    int count;
    LinkedList<Courseware> coursewareLinkedList;

    CoursewareReceive() {
        coursewareLinkedList = new LinkedList<>();
    }

    public int getCount() {
        return count;
    }

    public LinkedList<Courseware> getCoursewareLinkedList() {
        return coursewareLinkedList;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCoursewareLinkedList(LinkedList<Courseware> coursewareLinkedList) {
        this.coursewareLinkedList = coursewareLinkedList;
    }
}
