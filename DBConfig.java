package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfig {
    private final static String URL = "jdbc:mysql://localhost:3306/";
    private final static String USER = "postgres";
    private final static String PASSWORD = "123";
    public static Connection getDbConfig() {
        try{

        }catch (Exception e){
            System.out.println("Error during connection to database:" + excception .getMessage);
        }
        return null;
    }

}
