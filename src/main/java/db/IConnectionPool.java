package db;

import java.sql.Connection;

public interface IConnectionPool {
    Connection getConnection();

}
