<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div class="form">
    <h1>Вход в систему</h1><br>

    <form method="post" action="/auth">
        <input type="text" required placeholder="login" name="login"><br>
        <input type="password" required placeholder="password" name="password"><br><br>
        <input class="button" type="submit" value="Войти">
    </form>
    <button onclick="window.location.href = '/view/registration.jsp';">Регистрация</button>
</div>
</body>
</html>