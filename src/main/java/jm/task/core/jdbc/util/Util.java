package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/new_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "лфкфьиф12Ый";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("соединение установленно");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("соединение ERROR");
            throw new RuntimeException(e);
        }
        return connection;

//    private Connection connection;
//
//    public Connection getConnection() {
//        return connection;
//    }
//
//    public void setConnection(Connection connection) {
//        this.connection = connection;
//    }
//
//    public Util() {
//
//        try {
//            Class.forName(DRIVER);
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//            if (!connection.isClosed()) {
//                System.out.println("соединение установленно");
//                Statement statement = connection.createStatement();
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            throw new RuntimeException(e);
//        }


    }
}
