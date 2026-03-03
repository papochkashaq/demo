<%@ page import="com.alderson.demo.controller.UsersController" %>
<%@ page import="java.util.List" %>
<%@ page import="com.alderson.demo.model.UserModel" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- Calling Class.forName in JSP is inefficient and shouldn't be here. Database initialization belongs in a ServletContextListener. --%>
<% try {
    Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
    e.printStackTrace();
}
    // Fetching data directly from controller in JSP bypasses MVC. Use a Servlet to set the data as an attribute.
    List<UserModel> usersList = UsersController.controller.getAllUsersFromDB();
%>
<link rel="stylesheet" href="../../resources/style.css" />
<!DOCTYPE html>

<html>
<head>
    <title>All users</title>
</head>
<body>
<table>
    <caption>All users</caption>
    <thead>
    <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Date of Birth</th>
        <th>Created At</th>
        <th>Updated At</th>
    </tr>
    </thead>
    <tbody>
    <% for (UserModel user : usersList) { %>
    <tr>
        <td><%= user.getName() %>
        </td>
        <td><%= user.getEmail() %>
        </td>
        <td><%= user.getDateOfBirth() %>
        </td>
        <td><%= user.getCreatedAt() %>
        </td>
        <td><%= user.getUpdatedAt() %>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
<p><a href="/">Return to the main page.</a></p>
</body>
</html>
