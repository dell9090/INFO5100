package edu.neu.app;

import java.sql.*;

public class BaseDAO {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/INFO5100";
    static final String USER = "root";
    static final String PASS = "1qaz2wsx";

    static Connection connection = null;
    static PreparedStatement preparedStatement = null;

    BaseDAO() {
        try {
            Class.forName(JDBC_DRIVER);// 指定连接类型
            connection = DriverManager.getConnection(DB_URL, USER, PASS);// 获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String sql) {
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

        } catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean change(String sql) {
        boolean flag = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public int getCount(String sql) {
        int count=0;
        try {
            preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();
            resultSet.last();
            count=resultSet.getRow();
            resultSet.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public  void close() {
        try {
            this.connection.close();
            this.preparedStatement.close();
        } catch(SQLException se){
            se.printStackTrace();
        }
    }
}
