package edu.neu.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

class UserDAO {

    private static UserDAO unique;
    private static BaseDAO db = null;
    private static MD5Security m = null;
    private UserDAO() {
        db = new BaseDAO();
        m = new MD5Security();
    }

    static UserDAO getUserDAO() {
        if (unique == null) {
            unique = new UserDAO();
        }
        return unique;
    }

    List<User> getUsers() {
        String sql = "SELECT * FROM Users";
        List<User> usrList = new ArrayList<>();

        db = new BaseDAO();// 创建BaseDAO对象
        ResultSet rs = null;
        try {
            rs = db.query(sql);// 执行语句，得到结果集

            // 显示数据
            while(rs.next()) {
                // 通过字段检索
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password= rs.getString("password");
                User u = new User(id, name, password);
                usrList.add(u);
            }
            db.close();
            rs.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return usrList;
        //return new ArrayList<>(users.values());
    }


    User createUser(User input) {
        User u = new User(input.getId(), input.getName(), input.getPassword());
        String pw = m.encrypt(u.getPassword());
        String sql = "INSERT INTO Users(id, name, password)"
                + " VALUES (" + u.getId()+ ",'" + u.getName() + "','" + pw + "')";
        System.out.println(sql);
        db = new BaseDAO();
        try {
            db.change(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        u.setPassword("invisible");
        return u;
    }

    public boolean deleteUser(int id) {
        db = new BaseDAO();
        boolean flag = false;
        String delete = "DELETE FROM Users WHERE id = " + Integer.toString(id);
        flag = db.change(delete);
        db.close();
        return flag;
    }

    public Response updateUser(User u){
        db = new BaseDAO();
        Response response = new Response();
        String sql = "SELECT * FROM Users WHERE id = " + Integer.toString(u.getId());
        ResultSet rs = null;
        try {
            rs = db.query(sql);// 执行语句，得到结果集

            if (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");

                response.setName(name);
                response.setUserid(id);

                if (u.getPassword().equals(password)) {
                    String update = "UPDATE Users SET password = " +u.getName() +
                            " WHERE id = " + Integer.toString(u.getId());
                    db.change(update);
                    response.setStatus(true);

                }
            }
            db.close();
            rs.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return response;
    }

    public Response checkExist(User u) {
        db = new BaseDAO();
        Response response = new Response();
        String sql = "SELECT * FROM Users WHERE id = " + Integer.toString(u.getId());
        ResultSet rs = null;
        try {
            rs = db.query(sql);// 执行语句，得到结果集

            // 显示数据
            while(rs.next()) {
                // 通过字段检索
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String s = m.decrypt(password);
                if (u.getPassword().equals(s)) {
                    response.setStatus(true);
                    response.setName(name);
                    response.setUserid(id);
                }
            }
            db.close();
            rs.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return response;
    }

    private static void getCount() {
        db=new BaseDAO();
        String getCountString="Select * From Users";
        int count = db.getCount(getCountString);
    }
}
