package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/dbcsit228f1";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("DB connected successfully");
            Statement statement = c.createStatement();
            String sql1 = "CREATE TABLE IF NOT EXISTS new_users (" +
                    "userid INT AUTO_INCREMENT KEY PRIMARY KEY," +
                    "name VARCHAR(100)," +
                    "email VARCHAR(100)," +
                    "password VARCHAR(100))";
            statement.execute(sql1);
            String sql2 = "CREATE TABLE IF NOT EXISTS todolist (" +
                    "listid INT AUTO_INCREMENT KEY PRIMARY KEY," +
                    "uid INT," +
                    "text VARCHAR(100)," +
                    "date VARCHAR(100))";
            statement.execute(sql2);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }
    public static void main(String[] args) {
        Connection c = getConnection();
        try {
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
