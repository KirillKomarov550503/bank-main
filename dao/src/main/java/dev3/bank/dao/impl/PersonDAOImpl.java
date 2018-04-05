package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.PersonDAO;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.Person;
import dev3.bank.entity.Role;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.stream.Collectors;

public class PersonDAOImpl implements PersonDAO {
    private static PersonDAOImpl personDAO;

    private PersonDAOImpl() {
        try (Connection connection = DriverManager.getConnection(DataBase.getURL(), DataBase.getNAME(), DataBase.getPASSWORD())) {
            PreparedStatement createTable = connection.prepareStatement("" +
                    "CREATE TABLE IF NOT EXISTS PERSON(" +
                    "id SERIAL NOT NULL PRIMARY KEY ," +
                    "name VARCHAR(100) NOT NULL," +
                    "surname VARCHAR(100) NOT NULL," +
                    "phone_number INT NOT NULL," +
                    "role CHAR(6) NOT NULL)");
            createTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Person> getByRole(Role role) {
        return getEntityMapValues()
                .stream()
                .filter(person -> person.getRole().equals(role))
                .collect(Collectors.toList());
    }
}
