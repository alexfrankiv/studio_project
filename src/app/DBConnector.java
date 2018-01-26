package app;

import java.sql.*;

public class DBConnector {

    public static DBConnector shared = new DBConnector();

    private static final String connUrl = "jdbc:mysql://localhost/studio?"+"user=root&password=root";

    private Connection connection = null;

    private DBConnector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connUrl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finalize() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnect() {
        return connection;
    }
}
