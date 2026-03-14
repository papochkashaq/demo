package com.alderson.demo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.alderson.demo.UsersApp;
import com.alderson.demo.service.UserDAO;
import com.alderson.demo.service.UserDTO;
import com.alderson.demo.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UsersController extends HttpServlet {

    private UserService userService = new UserService(new UserDAO(UsersApp.getConnection()));
    public static UsersController controller = new UsersController();
    // Static initialization of the controller can lead to issues with class loading order. Use it only where needed.

    // single responsibility problem, class runs the whole application, inits himself and db, separate logic and
    // app lifesycle
    // This method is never called in the servlet/JSP context. Consider using a ServletContextListener to
    // initialize the DB.

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        List<UserDTO> users = null;
        try {
            users = userService.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/users.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String name = request.getParameter("name");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String email = request.getParameter("email");
        try {
            if (addUser(name, email, dateOfBirth)) {
                response.sendRedirect("/users");
            } else {
                response.sendRedirect("/users/email-error");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addUser(String name, String email, String dateOfBirth) throws SQLException {
        // No validation for parameters (name, email, dateOfBirth). Should handle potential nulls or empty strings
        // before processing.
        if (UserDAO.isEmailFree(email)) {
            UserDAO.insertUser(new UserDTO(name, email, dateOfBirth));
            return true;
        } else {
            return false;
        }
    }
}
