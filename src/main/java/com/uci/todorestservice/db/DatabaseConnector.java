package com.uci.todorestservice.db;

import java.sql.*;


public class DatabaseConnector {


    private DatabaseConnector() {

    }

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb?useSSL=false",
                    "root", "Password");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


}
