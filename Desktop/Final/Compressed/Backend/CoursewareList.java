package edu.neu.app;

import java.util.LinkedList;

public class CoursewareList {
    int count;
    LinkedList<Courseware> coursewareLinkedList;

    CoursewareList() {
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
