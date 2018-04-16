package dev3.bank.dao.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBase {
    private static final String URL = "jdbc:postgresql://localhost:5432/bank";
    private static final String NAME = "postgres";
    private static final String PASSWORD = "qwerty";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Database exception");
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }
    }

    public static void executeProperty(String propertyName) {
        PropertyDB propertyDB = new PropertyDB();
        try {
            String initTableSQL = Files.readAllLines(Paths.get(propertyDB.getProperty(propertyName)))
                    .stream()
                    .reduce("", (acc, string) -> acc + string);
            PreparedStatement preparedStatement = getConnection().prepareStatement(initTableSQL);
            preparedStatement.execute();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


}
