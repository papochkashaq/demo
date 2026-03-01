<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="POST" action="/processCreate">
    <p>Name<input type="text" name="name" required></p>
    <p>Email<input type="email" name="email" required></p>
    <p>Date of birth<input type="date" name="dateOfBirth"></p>
    <p><input type="submit" value="Create user"></p>
</form>
<p><a href="/">Return to the main page.</a></p>
</body>
</html>
