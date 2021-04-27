package db;

public class DataSource {
    private static String username = "postgres";
    private static String password = "4427";
    private static String url = "jdbc:postgresql://127.0.0.1:5432/postgres";


    public DataSource() {
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUrl() {
        return url;
    }
}
