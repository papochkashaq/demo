<%@ page import="com.alderson.demo.controller.UsersController" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
</head>
<body>
<div class="container">
    <%
        String name = request.getParameter("name");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String email = request.getParameter("email");
        UsersController.controller.addUser(name, email, dateOfBirth);
        response.sendRedirect("/users");
    %>
</div>
</body>
</html>
