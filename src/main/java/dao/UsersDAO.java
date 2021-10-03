package dao;

import executor.Executor;
import models.User;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO {

    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public User getUserByLogin(String login) throws SQLException {
        return executor.execQuery("select * from users where login=" + "'"+login+"';", result -> {
            result.next();
            return new User(result.getString("login"),
                    result.getString("password"),
                    result.getString("email"));
        });
    }

    public User getUserBySessionId(String sessionId) throws SQLException {
        return executor.execQuery("select * from users where session_id='" + sessionId+"';", result -> {
            result.next();
            return new User(result.getString("login"),
                    result.getString("password"),
                    result.getString("email"));
        });
    }

    public void insertUser(User user) throws SQLException {
        executor.execUpdate("insert into users values (1,'" + user.getLogin() + "','" + user.getPassword() + "','" + user.getEmail() + "',NULL);");
    }

    public void updateUserSessionId(String sessionId, User user) throws SQLException {
        executor.execUpdate("update users set session_id ='" + sessionId + "' where login ='" + user.getLogin()+"'");
    }

    public void deleteSession(String sessionId) throws SQLException {
        executor.execUpdate("update users set session_id = NULL where session_id ='" + sessionId+"';");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users " +
                "(id bigint auto_increment, login varchar(256), password varchar(256)," +
                "email varchar(256), session_id varchar(256),  primary key (id));");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}

