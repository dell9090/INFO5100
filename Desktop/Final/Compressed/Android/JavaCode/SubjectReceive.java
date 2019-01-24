package com.example.xin.myapplication;

import java.util.ArrayList;
import java.util.List;

public class SubjectReceive {

    private int count;
    private List<Course> courses;
    SubjectReceive() {
        courses = new ArrayList<>();
    }
    public int getCount() {
        return count;
    }

    public int[] getSubjectlist() {
        int[] temp = new int[100];
        for (int i = 0; i < courses.size(); i++) {
            temp[i] = courses.get(i).getCourseid();
        }
        return temp;
    }

    public String[] getNamelist() {
        String[] temp = new String[100];
        for (int i = 0; i < courses.size(); i++) {
            temp[i] = courses.get(i).getTitle();
        }
        return temp;
    }
}
