import db.BasicConnectionPool;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class DataExporter {

    private static final String userExpected = "user_expected.csv";
    private static final String userResult = "user_result.csv";
    private static final String carExpected = "car_result.csv";
    private static final String carResult = "car_result.csv";
    private static final String logFile = "log.txt";
    private static List<String> userColumns = List.of("id","name");
    private static List<String> carColumns = List.of("id","brand");


    public static void main(String[] args) throws SQLException, IOException {

       // exportDataFromDb(userResult,"user",userColumns);
       // exportDataFromDb(carResult,"car",carColumns);
        CsvComparer csvComparer = new CsvComparer(userExpected,userResult,logFile);
        csvComparer.compareCsv();
    }

    private static void exportDataFromDb(String fileOut,String tableName,List<String> columns) throws IOException, SQLException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileOut));
        Connection connection = BasicConnectionPool.getInstance().getConnection();
        Statement statement = connection.createStatement();
        String query = "select * from \""+tableName+"\" order by id";
        ResultSet result = statement.executeQuery(query);
        while (result.next()){
            StringBuffer totalLine = new StringBuffer();
            for (int i = 0; i < columns.size(); i++) {
                if(i == columns.size()-1){
                    totalLine.append(result.getObject(columns.get(i)).toString());
                }else totalLine.append(result.getObject(columns.get(i)).toString()+",");
            }
            totalLine.append("\n");
            fileWriter.write(totalLine.toString());
        }
        fileWriter.close();
        statement.close();
    }
}
