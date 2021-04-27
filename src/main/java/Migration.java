import db.BasicConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Migration {

    public static void executeMigration(String migration) {
        Connection connection = BasicConnectionPool.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute(migration);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
