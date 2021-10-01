<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<div class="form">
    <h1>Регистрация</h1><br>

    <form method="post" action="/registration">
        <input type="email" required placeholder="email" name="email"><br>
        <input minlength="4" type="text" required placeholder="login" name="login"><br>
        <input minlength="4" type="password" required placeholder="password" name="password"><br><br>
        <input class="button" type="submit" value="Зарегистрироваться">
    </form>
</div>
</body>
</html>