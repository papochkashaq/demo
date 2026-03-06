<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Calling Class.forName in JSP is inefficient and shouldn't be here. Database initialization belongs in a ServletContextListener. --%>
<% try {
    Class.forName("org.postgresql.Driver");
} catch (ClassNotFoundException e) {
    e.printStackTrace();
}
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
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>${user.dateOfBirth}</td>
            <td>${user.createdAt}</td>
            <td>${user.updatedAt}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="/">Return to the main page.</a></p>
</body>
</html>
