package main;

import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String connectionString = "jdbc:mysql://localhost:3306/bookshop?serverTimezone=Europe/Moscow&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String login = "root";
    private static final String password = "";
    private static Connection connection;
    private static boolean initiated = false;

    public static void connect() {
        try {
            connection = DriverManager.getConnection(connectionString, login, password);
            initiated = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static AdminAccount getAdminByLoginAndPassword(String login, String password) {
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(String.format("SELECT * FROM schooltest.adminaccounts WHERE login='%s' AND password='%s'", login, password));
            AdminAccount adminAccount = null;
            if (results.next()) {
                int db_id = results.getInt("id");
                String db_login = results.getString("login");
                String db_pass = results.getString("password");
                String db_username = results.getString("username");
                adminAccount = new AdminAccount(db_id, db_login, db_pass, db_username);
            }
            statement.close();
            return adminAccount;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<TeacherAccount> selectTeachers() {
        List<TeacherAccount> teacherAccounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM schooltest.teacheraccounts");
            TeacherAccount teacherAccount = null;
            while (resultSet.next()) {
                int db_id = resultSet.getInt("id");
                String db_login = resultSet.getString("login");
                String db_pass = resultSet.getString("password");
                String db_username = resultSet.getString("username");
                teacherAccount = new TeacherAccount(db_id, db_login, db_pass, db_username);
                teacherAccounts.add(teacherAccount);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherAccounts;
    }

    public static TeacherAccount getTeacherByLoginAndPassword(String login, String password) {
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(String.format("SELECT * FROM schooltest.teacheraccounts WHERE login='%s' AND password='%s'", login, password));
            TeacherAccount teacherAccount = null;
            if (results.next()) {
                int db_id = results.getInt("id");
                String db_login = results.getString("login");
                String db_pass = results.getString("password");
                String db_username = results.getString("username");
                teacherAccount = new TeacherAccount(db_id, db_login, db_pass, db_username);
            }
            statement.close();
            return teacherAccount;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static StudentAccount getStudentByLoginAndPassword(String login, String password) {
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(String.format("SELECT * FROM schooltest.studentaccounts WHERE login='%s' AND password='%s'", login, password));
            StudentAccount studentAccount = null;
            if (results.next()) {
                int db_id = results.getInt("id");
                String db_login = results.getString("login");
                String db_pass = results.getString("password");
                String db_username = results.getString("username");
                String db_classname = results.getString("class_name");
                studentAccount = new StudentAccount(db_id, db_login, db_pass, db_username, db_classname);
            }
            statement.close();
            return studentAccount;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static StudentAccount getStudentByLogin(String login) {
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(String.format("SELECT * FROM schooltest.studentaccounts WHERE login='%s'", login));
            StudentAccount studentAccount = null;
            if (results.next()) {
                int db_id = results.getInt("id");
                String db_login = results.getString("login");
                String db_pass = results.getString("password");
                String db_username = results.getString("username");
                String db_classname = results.getString("class_name");
                studentAccount = new StudentAccount(db_id, db_login, db_pass, db_username, db_classname);
            }
            statement.close();
            return studentAccount;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addStudent(StudentAccount studentAccount) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(String.format(
                    "INSERT INTO schooltest.studentaccounts (login, password, username) VALUES ('%s', '%s', '%s')",
                    studentAccount.getLogin(), studentAccount.getPassword(), studentAccount.getUsername())
            );
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        if (initiated) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Test> selectAllTests() {
        return null;
    }

    public static List<Question> selectQuestion() {return null;}
}
