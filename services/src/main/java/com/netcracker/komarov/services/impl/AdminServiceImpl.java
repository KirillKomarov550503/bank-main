package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Admin;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.interfaces.AdminDAO;
import com.netcracker.komarov.dao.interfaces.PersonDAO;
import com.netcracker.komarov.dao.utils.DataBase;
import com.netcracker.komarov.services.factory.DAOFactory;
import com.netcracker.komarov.services.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class AdminServiceImpl implements AdminService {
    private AdminDAO adminDAO;
    private PersonDAO personDAO;

    @Autowired
    public AdminServiceImpl(DAOFactory daoFactory) {
        this.adminDAO = daoFactory.getAdminDAO();
        this.personDAO = daoFactory.getPersonDAO();
    }

    @Override
    public Admin addAdmin(Person person) {
        Connection connection = DataBase.getConnection();
        Admin adminRes = null;
        try {
            connection.setAutoCommit(false);
            Admin admin = new Admin();
            admin.setPersonId(personDAO.add(person).getId());
            adminRes = adminDAO.add(admin);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                System.out.println("SQL exception");
            }
            System.out.println("SQL exception");
        }
        return adminRes;
    }

    @Override
    public Collection<Admin> getAllAdmin() {
        Collection<Admin> admins = null;
        try {
            admins = adminDAO.getAll();
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }
        return admins;
    }
}
