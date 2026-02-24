<%@ page import="com.alderson.demo.contoller.UsersController" %>
<%@ page import="javax.mail.internet.AddressException" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
</head>
<body>
<div class="container">
    <% PrintWriter writer = response.getWriter();
        String name = request.getParameter("name");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String email = request.getParameter("email");
//        while (true) {
//            try {
//                if (UsersController.controller.userService.isValidEmailAddress(email)) {
//                    break;
//                } else {
//                    email = request.getParameter("email");
//                }
//            } catch (Exception e) {
//                writer.println();
//            }
//        }
        UsersController.controller.addUser(name, email, dateOfBirth);
        response.sendRedirect("users.jsp");
    %>
</div>
</body>
</html>
