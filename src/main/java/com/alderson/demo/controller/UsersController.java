package com.alderson.demo.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alderson.demo.database.PostgresDAO;
import com.alderson.demo.model.UserModel;

public class UsersController implements Controller {

    public static UsersController controller = UsersController.getInstance();

    public List<UserModel> getAllUsersFromDB() throws SQLException {
        List<UserModel> usersList = new ArrayList<>();
        ResultSet rs = PostgresDAO.getAllUsers();
        while (rs.next()) {
            usersList.add(new UserModel(rs.getString("name"), rs.getString("email"), rs.getDate("dateOfBirth").toLocalDate(), rs.getTimestamp("createdAt"), rs.getTimestamp("updatedAt")));
        }
        return usersList;
    }

    public boolean addUser(String name, String email, String dateOfBirth) throws SQLException {
       if (PostgresDAO.isEmailFree(email)) {
           PostgresDAO.insertUser(new UserModel(name, email, dateOfBirth));
           return true;
       }
       else
           return false;
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

    public void run() {
        PostgresDAO.initDB();
    }
}
