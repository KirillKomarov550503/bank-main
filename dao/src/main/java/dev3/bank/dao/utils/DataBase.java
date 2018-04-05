package dev3.bank.dao.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static final String URL = "jdbc:postgresql://localhost:5432/test";
    private static final String NAME = "postgres";
    private static final String PASSWORD = "qwerty";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
        }
        return connection;
    }

    public static String getURL() {
        return URL;
    }

    public static String getNAME() {
        return NAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }


}
