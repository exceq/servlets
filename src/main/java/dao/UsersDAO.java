package dao;

import dao.sqlscripts.TableManagement;
import dao.sqlscripts.UserManagement;
import executor.Executor;
import models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO implements UserDAO {

    private final Executor executor;
    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public User getUserByLogin(String login) throws SQLException {
        return executor.execQuery(UserManagement.getUserByLoginScript(), this::extractUserFromResultSet, login);
    }

    public User getUserBySessionId(String sessionId) throws SQLException {
        return executor.execQuery(UserManagement.getUserBySessionIdScript(), this::extractUserFromResultSet, sessionId);
    }

    private User extractUserFromResultSet(ResultSet result) throws SQLException {
        if (result.next()){
            return new User(result.getString("login"),
                            result.getString("password"),
                            result.getString("email"));
        }
        return null; // User not found
    }

    public void insertUser(User user) throws SQLException {
        executor.execUpdate(UserManagement.getInsertUserScript(), user.getLogin(), user.getPassword(), user.getEmail());
    }

    public void updateUserSessionId(String sessionId, User user) throws SQLException {
        executor.execUpdate(UserManagement.getUpdateUserSessionIdScript(), sessionId, user.getLogin());
    }

    public void deleteSession(String sessionId) throws SQLException {
        executor.execUpdate(UserManagement.getDeleteSessionIdScript(), sessionId);
    }

    public void createTable() throws SQLException {
        executor.execUpdate(TableManagement.getCreatingTableScript());
    }

    public void dropTable() throws SQLException {
        executor.execUpdate(TableManagement.getDropTableScript());
    }
}

