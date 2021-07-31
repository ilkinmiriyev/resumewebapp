<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 09.06.2021
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/users.css" rel="stylesheet">
</head>
<body>

<form action="login" method="POST">
    <div class="container">
        <div class="form-group col-md-4">
            <label>Email address</label>
            <input type="email" class="form-control" placeholder="Enter email" name="email">
        </div>
        <div class="form-group col-md-4">
            <label>Password</label>
            <input type="password" class="form-control" placeholder="Password" name="password">
        </div>

        <button type="submit" class="btn btn-primary" name="login">Login</button>
    </div>
</form>

</body>
</html>
