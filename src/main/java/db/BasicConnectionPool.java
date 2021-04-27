package db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasicConnectionPool implements  IConnectionPool{
    private static DataSource dataSource;
    private static BasicConnectionPool instance = null;  // lazy loading
    private static List<Connection> connectionPool;
    private static final int INITIAL_POOL_SIZE = 20;

    private BasicConnectionPool(List<Connection> newPool) {
        connectionPool = newPool;
    }

    private BasicConnectionPool() {
        connectionPool = new ArrayList<>();
        getInstance();
    }
    @Override
    public Connection getConnection() {
        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);
        return connection;
    }

    public static BasicConnectionPool getInstance() {
        if (instance == null) {
            List<Connection> pool = new ArrayList<Connection>(INITIAL_POOL_SIZE);
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                try {
                    Connection connection = createConnection(db.DataSource.getUrl(), db.DataSource.getUsername(), db.DataSource.getPassword());
                    pool.add(connection);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            instance = new BasicConnectionPool(pool);
            return instance;
        }
        return instance;
    }


    private static Connection createConnection(
            String url, String user, String password)
            throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}
