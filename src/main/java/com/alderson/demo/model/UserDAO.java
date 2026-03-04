package com.alderson.demo.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDAO {
    private static Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public List<UserDTO> getAllUsers() throws SQLException {
        // PreparedStatement and ResultSet are not closed. Use try-with-resources or close them manually. Returning a
        // ResultSet to the controller/view is a leak.

        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT name, email, dateOfBirth, createdAt, updatedAt FROM users")) {
            ResultSet rs = stmt.executeQuery();
            List<UserDTO> usersList = new ArrayList<>();
            while (rs.next()) {
                usersList.add(new UserDTO(rs.getString("name"), rs.getString("email"),
                        rs.getDate("dateOfBirth").toLocalDate(), rs.getTimestamp("createdAt"), rs.getTimestamp(
                                "updatedAt"
                )));
            }
            return usersList;
        }
    }

    public static boolean isEmailFree(String email) throws SQLException {
        String sql = "SELECT NOT EXISTS (SELECT 1 FROM users WHERE email = ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        }
        return false;
    }

    public static void insertUser(UserDTO userDTO) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        try (PreparedStatement insertStmt = connection.prepareStatement("INSERT INTO users(name, email, dateOfBirth, "
                + "createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)");) {
            insertStmt.setString(1, userDTO.getName());
            insertStmt.setString(2, userDTO.getEmail());
            insertStmt.setDate(3, Date.valueOf(userDTO.getDateOfBirth()));
            insertStmt.setTimestamp(4, now);
            insertStmt.setTimestamp(5, now);
            insertStmt.execute();
        } catch (SQLException e) {
            System.out.println("Error insert user to database " + Arrays.toString(e.getStackTrace()));
        }
        // doesn't execute call return boolean?
    }
}
