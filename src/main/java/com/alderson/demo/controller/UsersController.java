package com.alderson.demo.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alderson.demo.database.PostgresDAO;
import com.alderson.demo.model.UserModel;

public class UsersController implements Controller {

    public static UsersController controller = UsersController.getInstance();
    // Static initialization of the controller can lead to issues with class loading order. Use it only where needed.

    public void run() {
        PostgresDAO.initDB();
        // single responsibility problem, class runs the whole application, inits himself and db, separate logic and app lifesycle
        // This method is never called in the servlet/JSP context. Consider using a ServletContextListener to initialize the DB.
    }

    public List<UserModel> getAllUsersFromDB() throws SQLException {
        List<UserModel> usersList = new ArrayList<>();
        ResultSet rs = PostgresDAO.getAllUsers();
        while (rs.next()) {
            usersList.add(new UserModel(rs.getString("name"), rs.getString("email"),
                    rs.getDate("dateOfBirth").toLocalDate(), rs.getTimestamp("createdAt"), rs.getTimestamp("updatedAt"
            )));
        }
        return usersList;
    }

    public boolean addUser(String name, String email, String dateOfBirth) throws SQLException {
        // No validation for parameters (name, email, dateOfBirth). Should handle potential nulls or empty strings before processing.
        if (PostgresDAO.isEmailFree(email)) {
            PostgresDAO.insertUser(new UserModel(name, email, dateOfBirth));
            return true;
        } else {
            return false;
        }
    }

    public static UsersController getInstance() {
        if (controller == null) {
            synchronized (UsersController.class) {
                if (controller == null) {
                    controller = new UsersController();
                }
            }
        }
        return controller;
    }

}
