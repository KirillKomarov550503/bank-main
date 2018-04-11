package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.PersonDAO;
import dev3.bank.entity.Person;
import dev3.bank.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class PersonDAOImpl implements PersonDAO {
    private static PersonDAOImpl personDAO;
    private Connection connection;

    private PersonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public static synchronized PersonDAOImpl getPersonDAO(Connection connection) {
        if (personDAO == null) {
            personDAO = new PersonDAOImpl(connection);
        }
        return personDAO;
    }

    @Override
    public Collection<Person> getByRole(Role role) throws SQLException {
        Collection<Person> people = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Person WHERE role=?");
        preparedStatement.setString(1, role.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            people.add(getPerson(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return people;
    }

    private Person getPerson(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getLong("id"));
        person.setName(resultSet.getString("name"));
        person.setSurname(resultSet.getString("surname"));
        person.setLogin(resultSet.getString("login"));
        person.setPassword(resultSet.getString("password"));
        person.setPassportId(resultSet.getLong("passport_id"));
        person.setPhoneNumber(resultSet.getInt("phone_number"));
        switch (resultSet.getString("role")) {
            case "CLIENT":
                person.setRole(Role.CLIENT);
                break;
            case "ADMIN":
                person.setRole(Role.ADMIN);
                break;
        }
        return person;
    }

    @Override
    public Person update(Person entity) throws SQLException {
        Person person = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE Person SET name=?, surname=?, login=?, password=?, passport_id=?, role=?, phone_number=? WHERE id=?");
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setString(2, entity.getSurname());
        preparedStatement.setString(3, entity.getLogin());
        preparedStatement.setString(4, entity.getPassword());
        preparedStatement.setLong(5, entity.getPassportId());
        preparedStatement.setString(6, entity.getRole().toString());
        preparedStatement.setLong(7, person.getPhoneNumber());
        preparedStatement.setLong(8, person.getId());
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Person WHERE id=?");
        preparedStatement.setLong(1, entity.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            person = getPerson(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return person;
    }

    @Override
    public Person getById(long id) throws SQLException {
        Person person = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Person WHERE id=?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            person = getPerson(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return person;
    }

    @Override
    public void delete(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "DELETE FROM Person WHERE  id=?");
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public Person add(Person entity) throws SQLException {
        Person person = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO Person(name, surname, login, password, phone_number, passport_id, role)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setString(2, entity.getSurname());
        preparedStatement.setString(3, entity.getLogin());
        preparedStatement.setString(4, entity.getPassword());
        preparedStatement.setLong(5, entity.getPhoneNumber());
        preparedStatement.setLong(6, entity.getPassportId());
        preparedStatement.setString(7, entity.getRole().toString());
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Person WHERE passport_id=?");
        preparedStatement.setLong(1, entity.getPassportId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            person = getPerson(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return person;
    }

    @Override
    public Collection<Person> getAll() throws SQLException {
        Collection<Person> people = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM person");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            people.add(getPerson(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return people;
    }
}
