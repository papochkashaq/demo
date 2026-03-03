package com.alderson.demo.database;

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
import java.util.Scanner;

import com.alderson.demo.model.UserModel;

public class PostgresDAO {
    //where is pojo for entity layer?

    private static Connection connection;
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream in = PostgresDAO.class.getClassLoader().getResourceAsStream("db.properties");) {
            PROPERTIES.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String URL = PROPERTIES.getProperty("db.url");
        final String USERNAME = PROPERTIES.getProperty("db.username");
        final String PASSWORD = PROPERTIES.getProperty("db.password");

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

    public static void initDB() {
        // single responsibility problem, class inits db and accesses it, separate data access part and db initiation
        Scanner scanner = new Scanner(PostgresDAO.class.getClassLoader().getResourceAsStream("schema.sql"));
        try {
            Statement stmt = connection.createStatement();
            while (scanner.hasNextLine()) {
                stmt.execute(scanner.nextLine());
            }
        } catch (SQLException e) {
            System.out.println("Error to init DB " + Arrays.toString(e.getStackTrace()));
        }

    }

    public static boolean insertUser(UserModel userModel) {
        // use try-with-resources for PreparedStatement to ensure it is closed properly
        try {
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            PreparedStatement insertStmt = connection.prepareStatement("INSERT INTO users(name, email, dateOfBirth, " +
                    "createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)");
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
        // doesn't execute call return boolean?
    }

    public static ResultSet getAllUsers() throws SQLException {
        // PreparedStatement and ResultSet are not closed. Use try-with-resources or close them manually. Returning a ResultSet to the controller/view is a leak.
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT name, email, dateOfBirth, createdAt, updatedAt FROM users");
        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public static boolean isEmailFree(String email) throws SQLException {
        // use try-with-resources for PreparedStatement and ResultSet
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT email FROM users WHERE email = ?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs == null || !rs.next()) {
            // do we need those returns here?
            return true;
        } else {
            return false;
        }
    }

}
