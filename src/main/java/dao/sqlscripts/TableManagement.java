package dao.sqlscripts;

public class TableManagement {
    public static String getCreatingTableScript() {
        return "CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT, " +
                "login VARCHAR(256), password VARCHAR(256), email VARCHAR(256)," +
                "session_id VARCHAR(256), PRIMARY KEY (id));";
    }

    public static String getDropTableScript() {
        return "DROP TABLE users;";
    }
}
