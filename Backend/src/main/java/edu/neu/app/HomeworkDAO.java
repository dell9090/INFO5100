package edu.neu.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class HomeworkDAO {
    private static HomeworkDAO unique;
    private static BaseDAO db = null;

    static HomeworkDAO getHomeworkDAO() {
        if (unique == null) {
            unique = new HomeworkDAO();
        }
        return unique;
    }

    public HomeworkList getList(int courseid) {
        String sql = "SELECT * FROM Homeworks WHERE courseid = " + Integer.toString(courseid);
        HomeworkList homeworkList = new HomeworkList();
        LinkedList<Homework> temp = new LinkedList<>();
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
                int attachment_id = rs.getInt("attachment_id");
                String description = rs.getString("description");
                String open_at = rs.getString("open_at");
                String close_at = rs.getString("close_at");
                Homework n = new Homework(id, title, attachment_id,
                        description, open_at, close_at);
                temp.add(n);
                count++;
            }
            homeworkList.setCount(count);
            homeworkList.setHomeworkList(temp);
            db.close();
            rs.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return homeworkList;
    }
}
