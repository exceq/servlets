package services;

import dao.UsersDAO;
import models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
    private final static Connection connection;

    private final static UsersDAO dao;

    static {
        connection = getMysqlConnection();
        dao = new UsersDAO(connection);
        try {
            new UsersDAO(connection).createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DBService(){}

    public static User getUserByLogin(String login) throws SQLException {
        return dao.getUserByLogin(login);
    }

    public static User getUserBySessionId(String sessionId) throws SQLException {
        return dao.getUserBySessionId(sessionId);
    }

    public static void addUser(User user) throws SQLException {
        dao.insertUser(user);
    }

    public static void addSession(String sessionId, User user) throws SQLException {
        dao.updateUserSessionId(sessionId, user);
    }

    public static void deleteSession(String sessionId) throws SQLException {
        dao.deleteSession(sessionId);
    }

    public static void cleanUp() throws SQLException {
        dao.dropTable();
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
