<%@ page import="com.alderson.demo.controller.UsersController" %>
<html>
<head>
    <title>Index</title>
</head>
<body>
<%UsersController.controller.run();%>
<p>Welcome to your users' database main page.</p>
<p><a href="/users/create">Create a user.</a></p>
<p><a href="/users">Show all users.</a></p>
</body>
</html>