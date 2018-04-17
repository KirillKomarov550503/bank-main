package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.AdminDAO;
import dev3.bank.entity.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class AdminDAOImpl implements AdminDAO {
    private static AdminDAOImpl adminDAO;
    private Connection connection;

    private AdminDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public static synchronized AdminDAOImpl getAdminDAO(Connection connection) {
        if (adminDAO == null) {
            adminDAO = new AdminDAOImpl(connection);
        }
        return adminDAO;
    }

    @Override
    public Admin getById(long id) throws SQLException {
        Admin admin = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM admin WHERE id=?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            admin = getAdmin(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return admin;
    }

    private Admin getAdmin(ResultSet resultSet) throws SQLException {
        Admin admin = new Admin();
        admin.setId(resultSet.getLong("id"));
        admin.setPersonId(resultSet.getLong("person_id"));
        return admin;
    }

    @Override
    public Admin update(Admin entity) throws SQLException {
        Admin admin = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE admin SET person_id=? WHERE id=?");
        preparedStatement.setLong(1, entity.getPersonId());
        preparedStatement.setLong(2, entity.getId());
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM admin WHERE id=?");
        preparedStatement.setLong(1, entity.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            admin = getAdmin(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return admin;
    }

    @Override
    public void delete(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "DELETE FROM admin WHERE id=?");
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public Admin add(Admin entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO admin(person_id) VALUES(?) RETURNING id");
        preparedStatement.setLong(1, entity.getPersonId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            entity.setId(resultSet.getLong("id"));
        }
        resultSet.close();
        preparedStatement.close();
        return entity;
    }

    @Override
    public Collection<Admin> getAll() throws SQLException {
        Collection<Admin> admins = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM admin");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            admins.add(getAdmin(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return admins;
    }
}
