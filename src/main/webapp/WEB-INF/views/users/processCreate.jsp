<%@ page import="com.alderson.demo.controller.UsersController" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create user</title>
</head>
<body>
<div class="container">
    <%
        // This JSP acts like a Controller. Instead of using JSP, use a Servlet to process the POST request and handle redirects.
        String name = request.getParameter("name");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String email = request.getParameter("email");
        if (UsersController.controller.addUser(name, email, dateOfBirth)) {
            response.sendRedirect("/users");
        } else { %>
    <p>This email is already taken. Please enter another email.</p>
    <p><a href="/users/create">Return to the create user page.</a></p>
    <% }
    %>
</div>
</body>
</html>
