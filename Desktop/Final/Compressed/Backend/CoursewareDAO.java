package edu.neu.app;

import java.io.File;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public class CoursewareDAO {

    private static CoursewareDAO unique;
    private static BaseDAO db = null;

    static CoursewareDAO getCoursewareDAO() {
        if (unique == null) {
            unique = new CoursewareDAO();
        }
        return unique;
    }

    public static CoursewareList getCourseware(int courseid) {
        String sql = "SELECT * FROM Coursewares WHERE courseid = " + Integer.toString(courseid);
        CoursewareList coursewareList = new CoursewareList();
        LinkedList<Courseware> temp = new LinkedList<>();
        int count = 0;
        db = new BaseDAO();// 创建BaseDAO对象
        ResultSet rs = null;
        try {
            rs = db.query(sql);// 执行语句，得到结果集
            // 显示数据
            while(rs.next()) {
                // 通过字段检索
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String info= rs.getString("info");
                String dir = rs.getString("dir");
                Courseware n = new Courseware(id, courseid, dir, info, name);
                temp.add(n);
                count++;
            }
            coursewareList.setCount(count);
            coursewareList.setCoursewareLinkedList(temp);
            db.close();
            rs.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return coursewareList;
    }

    public static Response download(int id) {

        File file = null;
        String fileName = null;
        String dir = null;
        String info = null;

        String sql = "SELECT * FROM Coursewares WHERE id = " + Integer.toString(id);
        db = new BaseDAO();// 创建BaseDAO对象
        ResultSet rs = null;
        try {
            rs = db.query(sql);
            if (rs.next()) {
                // 通过字段检索
                dir = rs.getString("dir");
                info= rs.getString("info");
                fileName= URLEncoder.encode(rs.getString("name"), "UTF-8");
            }
            db.close();
            rs.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            file = new File(dir);

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        ResponseBuilder response = Response.ok((Object) file);
        response.type("application/x-msdownload");
        response.header("Content-Disposition", "attachment; filename=" + fileName);
        response.header("Content-Length", Long.toString(file.length()));

        return response.build();
    }

}
