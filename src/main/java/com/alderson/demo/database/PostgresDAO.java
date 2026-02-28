package com.alderson.demo.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Properties;

import com.alderson.demo.model.UserModel;

public class PostgresDAO {

    private static Connection connection;
    private static final Properties props = new Properties();

    static {
        try (InputStream in = PostgresDAO.class.getClassLoader().getResourceAsStream("db.properties");) {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String URL = props.getProperty("db.url");
        final String USERNAME = props.getProperty("db.username");
        final String PASSWORD = props.getProperty("db.password");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean initUsersTable() {
        try {
            Statement st = connection.createStatement();
            st.execute("CREATE TABLE users (" +
                    "  id SERIAL PRIMARY KEY," +
                    "  name VARCHAR(128)," +
                    "  email VARCHAR(128)," +
                    "  dateOfBirth DATE," +
                    "  createdAt TIMESTAMP," +
                    "  updatedAt TIMESTAMP" +
                    ");");
        } catch (SQLException e) {
            System.out.println("Error connecting to database " + Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }

    public static boolean insertUser(UserModel userModel) {
        try {
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            PreparedStatement insertStmt = connection.prepareStatement("INSERT INTO users(name, email, dateOfBirth, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)");
            insertStmt.setString(1, userModel.getName());
            insertStmt.setString(2, userModel.getEmail());
            insertStmt.setDate(3, Date.valueOf(userModel.getDateOfBirth()));
            insertStmt.setTimestamp(4, now);
            insertStmt.setTimestamp(5, now);
            insertStmt.execute();
        } catch (SQLException e) {
            System.out.println("Error insert user to database " + Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }

    public static ResultSet getAllUsers() throws SQLException {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT name, email, dateOfBirth, createdAt, updatedAt FROM users");
            ResultSet rs = stmt.executeQuery();
            return rs;
    }

    public static boolean clearTable(String table) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "TRUNCATE TABLE " + table);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Error to truncate table " + Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }

    public static boolean isEmailFree(String email) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT email FROM users WHERE email = ?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs == null || !rs.next()) {
            return true;
        }
        else return false;
    }
}
