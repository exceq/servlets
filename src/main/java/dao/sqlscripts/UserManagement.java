package dao.sqlscripts;

import models.User;

import java.net.PasswordAuthentication;

public class UserManagement {

    public static String getDeleteSessionIdScript(String sessionId) {
        return String.format("UPDATE users SET session_id = NULL " +
                "WHERE session_id = '%s';", sessionId);
    }

    public static String getUpdateUserSessionIdScript(String sessionId, User user) {
        return String.format("UPDATE users SET session_id ='%s'" +
                "WHERE login ='%s';", sessionId, user.getLogin());
    }

    public static String getInsertUserScript(User user) {
        return String.format("INSERT INTO users (login, password, email) " +
                "VALUES ('%s','%s'','%s');", user.getLogin(), user.getPassword(),user.getEmail());
    }

    public static String getUserByLoginScript(String login) {
        return String.format("SELECT * FROM users WHERE login='%s';", login);
    }

    public static String getUserBySessionIdScript(String sessionId) {
        return String.format("SELECT * FROM users WHERE session_id='%s';", sessionId);
    }
}
