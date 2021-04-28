import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserMigrationTest {
    private static final String userExpected = "user_expected.csv";
    private static final String userResult = "user_result.csv";
    private static final String logFile = "user_log.txt";

    @Test
    public void testNonMigratedUserDataBaseReturnAllRowsEquals() {
        try {
            DataExporter.exportDataFromDb(userResult, "user", DataExporter.getTableColumnsNames("user"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        CsvComparer csvComparer = new CsvComparer(userExpected, userResult, logFile);
        boolean equality = csvComparer.compareCsv();
        assertTrue(equality);
    }

    @Test
    public void migrateUserTableToNewColumn() {
        try {
            Migration.executeMigration(MigrationQuery.addUserNewColumnMigrationUp);
            DataExporter.exportDataFromDb(userResult, "user", DataExporter.getTableColumnsNames("user"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        CsvComparer csvComparer = new CsvComparer(userExpected, userResult, logFile);
        boolean equality = csvComparer.compareCsv();
        Migration.executeMigration(MigrationQuery.userTableMigrationDown);
        assertFalse(equality);
    }

}
