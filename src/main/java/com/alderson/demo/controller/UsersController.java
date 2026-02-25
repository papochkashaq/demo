package com.alderson.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.alderson.demo.database.PostgresDAO;
import com.alderson.demo.model.UserModel;
import com.alderson.demo.model.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UsersController implements Controller {

    public static UsersController controller = UsersController.getInstance();
    public UserService userService = UserService.getInstance();

    public void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<table>\n" +
                "<caption>All users</caption>\n" +
                "<thead>\n" +
                "  <tr>\n" +
                "    <th>Name</th>\n" +
                "    <th>Email</th>\n" +
                "    <th>Date of Birth</th>\n" +
                "    <th>Created At</th>\n" +
                "    <th>Updated At</th>\n" +
                "  </tr>\n" +
                "  </thead>\n" +
                "  <tbody>\n");
        out.println("<tr>");
                out.println("<td>" + "Andrey" + "</td>");
                out.println("<td>" + "a@mail.ru" + "</td>");
                out.println("<td>" + "19.12.1993" + "</td>");
                out.println("<td>" + "2021-12-06" + "</td>");
                out.println("<td>" + "2021-12-06" + "</td>");
                out.println("</tr>");
        out.println("</tbody>");
        out.println("</table>");

//        ResultSet rs = PostgresDAO.getAllUsers();
//        if (rs != null) {
//            while (rs.next()) {
//                out.println("<tr>");
//                out.println("<td>" + rs.getString("name") + "</td>");
//                out.println("<td>" + rs.getString("email") + "</td>");
//                out.println("<td>" + rs.getDate("dateOfBirth") + "</td>");
//                out.println("<td>" + rs.getTimestamp("createdAt") + "</td>");
//                out.println("<td>" + rs.getTimestamp("updatedAt") + "</td>");
//                out.println("</tr>");
//            }
//            out.println("</tbody>\n" + "</table>");
//        } else {
//            out.println("<p>Error to get users from database.</p>");
//        }

    }

    public List<UserModel> getAllUsersFromDB() throws SQLException {
        List<UserModel> usersList = new ArrayList<>();
        ResultSet rs = PostgresDAO.getAllUsers();
        while (rs.next()) {
            usersList.add(new UserModel(rs.getString("name"), rs.getString("email"), rs.getDate("dateOfBirth").toLocalDate(), rs.getTimestamp("createdAt"), rs.getTimestamp("updatedAt")));
        }
        return usersList;
    }

    public List<UserModel> getAllUsersByMockList() throws IOException, SQLException {
        List<UserModel> users = new ArrayList<>();
        users.add(new UserModel("A", "1@1", "1989-12-03"));
        users.add(new UserModel("B", "2@2", "1965-04-20"));
        return users;
    }

    public void addUser(String name, String email, String dateOfBirth) {
        PostgresDAO.insertUser(new UserModel(name, email, dateOfBirth));
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
