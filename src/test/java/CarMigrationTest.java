import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarMigrationTest {
    private static final String carExpected = "car_expected.csv";
    private static final String carResult = "car_result.csv";
    private static final String logFile = "car_log.txt";

    @Test
    public void testNonMigratedUserDataBaseReturnAllRowsEquals() {
        try {
            DataExporter.exportDataFromDb(carResult, "car", DataExporter.getTableColumnsNames("car"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        CsvComparer csvComparer = new CsvComparer(carExpected, carResult, logFile);
        boolean equality = csvComparer.compareCsv();
        assertTrue(equality);
    }

    @Test
    public void migrateUserTableToNewColumn() {
        try {
            Migration.executeMigration(MigrationQuery.addNewCarColumnMigrationUp);
            DataExporter.exportDataFromDb(carResult, "car", DataExporter.getTableColumnsNames("car"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        CsvComparer csvComparer = new CsvComparer(carExpected, carResult, logFile);
        boolean equality = csvComparer.compareCsv();
        Migration.executeMigration(MigrationQuery.carTableMigrationDown);
        assertFalse(equality);
    }
}
