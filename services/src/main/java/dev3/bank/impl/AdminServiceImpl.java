package dev3.bank.impl;

import dev3.bank.dao.interfaces.AdminDAO;
import dev3.bank.dao.interfaces.PersonDAO;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.Admin;
import dev3.bank.entity.Person;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.AdminService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class AdminServiceImpl implements AdminService {
    private static AdminServiceImpl adminService;
    private AdminDAO adminDAO;
    private PersonDAO personDAO;

    private AdminServiceImpl() {
    }

    public static synchronized AdminServiceImpl getAdminService() {
        if (adminService == null) {
            adminService = new AdminServiceImpl();
        }
        return adminService;
    }

    @Override
    public void setDAO(DAOFactory daoFactory) {
        adminDAO = daoFactory.getAdminDAO();
        personDAO = daoFactory.getPersonDAO();
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
            e.printStackTrace();
        }
        return admins;
    }
}
