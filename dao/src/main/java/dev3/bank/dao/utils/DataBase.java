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

    public static void executeProperty(String propertyName, String propPath) {
        PropertyDB propertyDB = new PropertyDB();
        propertyDB.setFilename(propPath);
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

    public static void initTable() {
        try {
            String sql = "" +
                    "CREATE TABLE IF NOT EXISTS Person (\n" +
                    "  id           BIGSERIAL   NOT NULL PRIMARY KEY,\n" +
                    "  name         VARCHAR(30) NOT NULL,\n" +
                    "  surname      VARCHAR(50) NOT NULL,\n" +
                    "  login        VARCHAR(30),\n" +
                    "  password     VARCHAR(30),\n" +
                    "  phone_number VARCHAR(20) NOT NULL,\n" +
                    "  passport_id  BIGINT      NOT NULL,\n" +
                    "  role         VARCHAR(10) NOT NULL\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS Client (\n" +
                    "  id        BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                    "  person_id BIGINT    NOT NULL REFERENCES Person (id)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS Account (\n" +
                    "  id         BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                    "  locked     BOOLEAN   NOT NULL,\n" +
                    "  balance    DECIMAL   NOT NULL,\n" +
                    "  account_id BIGINT    NOT NULL,\n" +
                    "  client_id  BIGINT    NOT NULL REFERENCES Client (id)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS Card (\n" +
                    "  id         BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                    "  locked     BOOLEAN   NOT NULL,\n" +
                    "  pin        INT       NOT NULL,\n" +
                    "  card_id    BIGINT    NOT NULL,\n" +
                    "  account_id BIGINT    NOT NULL REFERENCES Account (id)\n" +
                    ");\n" +
                    "\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS Admin (\n" +
                    "  id        BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                    "  person_id BIGINT    NOT NULL REFERENCES Person (id)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS News (\n" +
                    "  id          BIGSERIAL     NOT NULL PRIMARY KEY,\n" +
                    "  admin_id    BIGINT        NOT NULL REFERENCES Admin (id),\n" +
                    "  date        VARCHAR(30)   NOT NULL,\n" +
                    "  title       VARCHAR(100)  NOT NULL,\n" +
                    "  body        VARCHAR(1000) NOT NULL,\n" +
                    "  news_status VARCHAR(15)   NOT NULL\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS ClientNews (\n" +
                    "  id        BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                    "  news_id   BIGINT    NOT NULL REFERENCES News (id),\n" +
                    "  client_id BIGINT    NOT NULL\n" +
                    ");\n" +
                    "\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS Transaction (\n" +
                    "  id      BIGSERIAL   NOT NULL PRIMARY KEY,\n" +
                    "  date    VARCHAR(30) NOT NULL,\n" +
                    "  from_id BIGINT      NOT NULL REFERENCES Account (id),\n" +
                    "  to_id   BIGINT      NOT NULL REFERENCES Account (id),\n" +
                    "  money   DECIMAL(2)  NOT NULL\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABlE IF NOT EXISTS UnlockAccountRequest (\n" +
                    "  id         BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                    "  account_id BIGINT    NOT NULL REFERENCES Account (id)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS UnlockCardRequest (\n" +
                    "  id      BIGSERIAL NOT NULL PRIMARY KEY,\n" +
                    "  card_id BIGINT    NOT NULL REFERENCES Card (id)\n" +
                    ");\n" +
                    "\n" +
                    "\n";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropTable() {
        try {
            String sql = "" +
                    "DROP TABLE IF EXISTS transaction;\n" +
                    "DROP TABLE IF EXISTS unlockcardrequest;\n" +
                    "DROP TABLE IF EXISTS unlockaccountrequest;\n" +
                    "DROP TABLE IF EXISTS card;\n" +
                    "DROP TABLE IF EXISTS account;\n" +
                    "DROP TABLE IF EXISTS client;\n" +
                    "DROP TABLE IF EXISTS clientnews;\n" +
                    "DROP TABLE IF EXISTS news;\n" +
                    "DROP TABLE IF EXISTS admin;\n" +
                    "DROP TABLE IF EXISTS person;\n";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertValues() {
        try {
            String sql = "INSERT INTO person (name, surname, phone_number, passport_id, role)\n" +
                    "VALUES ('Kirill', 'Komarov', 123, 4234, 'CLIENT');\n" +
                    "\n" +
                    "INSERT INTO person (name, surname, phone_number, passport_id, role)\n" +
                    "VALUES ('Vladislav', 'Maznya', 54, 234, 'CLIENT');\n" +
                    "\n" +
                    "INSERT INTO person (name, surname, phone_number, passport_id, role)\n" +
                    "VALUES ('Pavel', 'Zaretskya', 1252, 643, 'CLIENT');\n" +
                    "\n" +
                    "INSERT INTO person (name, surname, phone_number, passport_id, role)\n" +
                    "VALUES ('Vladimir', 'Putin', 1111111, 01, 'ADMIN');\n" +
                    "\n" +
                    "\n" +
                    "INSERT INTO client (person_id) VALUES (1);\n" +
                    "INSERT INTO client (person_id) VALUES (2);\n" +
                    "INSERT INTO client (person_id) VALUES (3);\n" +
                    "\n" +
                    "\n" +
                    "INSERT INTO account (client_id, balance, locked, account_id)\n" +
                    "VALUES (1, 0, FALSE, 423);\n" +
                    "\n" +
                    "INSERT INTO account (client_id, balance, locked, account_id)\n" +
                    "VALUES (1, 0, FALSE, 100);\n" +
                    "\n" +
                    "INSERT INTO account (client_id, balance, locked, account_id)\n" +
                    "VALUES (3, 0, FALSE, 123);\n" +
                    "\n" +
                    "\n" +
                    "INSERT INTO card (account_id, card_id, pin, locked)\n" +
                    "VALUES (3, 100, 4234, FALSE);\n" +
                    "\n" +
                    "INSERT INTO card (account_id, card_id, pin, locked)\n" +
                    "VALUES (1, 128989, 1111, FALSE);\n" +
                    "\n" +
                    "INSERT INTO card (account_id, card_id, pin, locked)\n" +
                    "VALUES (1, 101, 4004, FALSE);\n" +
                    "\n" +
                    "\n" +
                    "INSERT INTO admin (person_id) VALUES (4);";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
