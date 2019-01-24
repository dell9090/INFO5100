package edu.neu.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CourseDAO {
    private static CourseDAO unique;
    private static BaseDAO db = null;

    static CourseDAO getCourseDAO() {
        if (unique == null) {
            unique = new CourseDAO();
        }
        return unique;
    }

    public CourseList getCourse(int studentId) {
        String sql = "SELECT * FROM Courses WHERE studentid = " + Integer.toString(studentId);
        CourseList courseList = new CourseList();
        List<Course> temp = new ArrayList<>();
        int count = 0;
        db = new BaseDAO();// 创建BaseDAO对象
        ResultSet rs = null;
        try {
            rs = db.query(sql);// 执行语句，得到结果集
            // 显示数据
            while(rs.next()) {
                // 通过字段检索
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int courseid = rs.getInt("courseid");
                Course c = new Course(id, courseid, title);
                temp.add(c);
                count++;
            }
            courseList.setCount(count);
            courseList.setCourses(temp);
            db.close();
            rs.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return courseList;
    }
}
