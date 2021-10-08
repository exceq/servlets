package executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(String updateQuery, String... args) throws SQLException {
        PreparedStatement stmt = prepareStatement(updateQuery, args);
        stmt.executeUpdate();
        stmt.close();
    }

    public <T> T execQuery(String query, ResultHandler<T> handler, String... args) throws SQLException {
        PreparedStatement stmt = prepareStatement(query, args);
        ResultSet result = stmt.executeQuery();
        T value = handler.handle(result);
        result.close();
        stmt.close();

        return value;
    }

    private PreparedStatement prepareStatement(String query, String... args) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        for (int i = 0; i < args.length; i++) {
            stmt.setString(i + 1, args[i]);
        }
        return stmt;
    }
}
