package edu.neu.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NotificationDAO {

    private static NotificationDAO unique;
    private static BaseDAO db = null;

    static NotificationDAO getNotificationDAO() {
        if (unique == null) {
            unique = new NotificationDAO();
        }
        return unique;
    }

    public NotificationList getNotifications(int courseid) {
        String sql = "SELECT * FROM Notifications WHERE courseid = " + Integer.toString(courseid);
        NotificationList notificationList = new NotificationList();
        LinkedList<Notification> temp = new LinkedList<>();
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
                String content= rs.getString("content");
                Notification n = new Notification(id, courseid, title, content);
                temp.add(n);
                count++;
            }
            notificationList.setCount(count);
            notificationList.setNotificationList(temp);
            db.close();
            rs.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return notificationList;
    }
}
