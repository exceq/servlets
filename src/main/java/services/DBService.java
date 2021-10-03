package services;

import dao.UsersDAO;
import models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
    private final static Connection connection;

    static {
        connection = getMysqlConnection();
        try {
            new UsersDAO(connection).createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DBService(){}

    public static User getUserByLogin(String login) throws SQLException {
        return new UsersDAO(connection).getUserByLogin(login);
    }

    public static User getUserBySessionId(String sessionId) throws SQLException {
        return new UsersDAO(connection).getUserBySessionId(sessionId);
    }

    public static void addUser(User user) throws SQLException {
        new UsersDAO(connection).insertUser(user);
    }

    public static void addSession(String sessionId, User user) throws SQLException {
        new UsersDAO(connection).updateUserSessionId(sessionId, user);
    }

    public static void deleteSession(String sessionId) throws SQLException {
        new UsersDAO(connection).deleteSession(sessionId);
    }

    public static void cleanUp() throws SQLException {
        new UsersDAO(connection).dropTable();
    }

    public static Connection getMysqlConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            StringBuilder url = new StringBuilder();

            url.append("jdbc:mysql://").        //db type
                append("localhost:").           //host name
                append("3306/").                //port
                append("mydb?").                //db name
                append("user=root&").           //login
                append("password=root");        //password

            return DriverManager.getConnection(url.toString());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
