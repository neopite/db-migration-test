public class MigrationQuery {
    public static String addUserNewColumnMigrationUp = "ALTER TABLE \"user\"\n" +
            "ADD COLUMN surname varchar(255) not null default 'obruch';";

    public static String userTableMigrationDown = "ALTER TABLE \"user\"\n" +
            "drop COLUMN surname;";

    public static String addNewCarColumnMigrationUp = "ALTER TABLE \"car\"\n" +
            "ADD COLUMN model varchar(255) not null default 'M5';";

    public static String carTableMigrationDown = "ALTER TABLE \"car\"\n" +
            "drop COLUMN model;";


}
