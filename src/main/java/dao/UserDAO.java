package dao;

import models.User;

import java.sql.SQLException;

public interface UserDAO {

    User getUserByLogin(String login) throws SQLException;

    User getUserBySessionId(String sessionId) throws SQLException;

    void insertUser(User user) throws SQLException;

    void updateUserSessionId(String sessionId, User user) throws SQLException;

    void deleteSession(String sessionId) throws SQLException;

    void createTable() throws SQLException;

    void dropTable() throws SQLException;
}
