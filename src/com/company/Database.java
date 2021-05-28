package com.company;

import com.company.TestElements.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    public static Connection connection;
    public static PreparedStatement globalSTMT;
    public static int currentUserId;

    public Database(String user, String password) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/system_test?serverTimezone=UTC", props);
    }
}
