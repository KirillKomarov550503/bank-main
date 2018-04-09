package dev3.bank.dao.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    public static void initTables() {
        PropertyDB propertyDB = new PropertyDB();
        try {
            String initTableSQL = Files.readAllLines(Paths.get(propertyDB.getProperty("init.table.path")))
                    .stream()
                    .reduce("", (acc, string) -> acc + string);
            PreparedStatement preparedStatement = getConnection().prepareStatement(initTableSQL);
            preparedStatement.execute();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
