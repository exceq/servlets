package dao.sqlscripts;

public class UserManagement {

    public static String getDeleteSessionIdScript() {
        return "UPDATE users SET session_id = NULL WHERE session_id = ?";
    }

    public static String getUpdateUserSessionIdScript() {
        return "UPDATE users SET session_id = ? WHERE login = ?";
    }

    public static String getInsertUserScript() {
        return "INSERT INTO users (login, password, email) VALUES (?,?,?)";
    }

    public static String getUserByLoginScript() {
        return "SELECT * FROM users WHERE login= ?";
    }

    public static String getUserBySessionIdScript() {
        return "SELECT * FROM users WHERE session_id= ?";
    }
}
