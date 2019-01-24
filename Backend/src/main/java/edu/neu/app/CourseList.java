package edu.neu.app;

import java.util.ArrayList;
import java.util.List;

public class CourseList {
    private int count;
    private List<Course> courses;
    CourseList() {
        courses = new ArrayList<>();
    }
    public int getCount() {
        return count;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
