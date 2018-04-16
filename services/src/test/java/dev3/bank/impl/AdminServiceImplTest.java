package dev3.bank.impl;

import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.Admin;
import dev3.bank.entity.Person;
import dev3.bank.entity.Role;
import dev3.bank.factory.DAOFactory;
import dev3.bank.factory.PostgreSQLDAOFactory;
import dev3.bank.interfaces.AdminService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class AdminServiceImplTest {
    private AdminService adminService;

    @Before
    public void init() {
        DataBase.dropTable();
        DataBase.initTable();
        DataBase.insertValues();
        DAOFactory daoFactory = PostgreSQLDAOFactory.getPostgreSQLDAOFactory();
        adminService = AdminServiceImpl.getAdminService();
        adminService.setDAO(daoFactory);
    }

    @Test
    public void addAdmin() {
        Admin admin = new Admin(2, 5);
        Person person = new Person();
        person.setName("Maxim");
        person.setSurname("Rahmanenko");
        person.setPassportId(52352);
        person.setPhoneNumber(4252);
        person.setRole(Role.ADMIN);
        Assert.assertEquals(admin, adminService.addAdmin(person));
    }

    @Test
    public void getAllAdmin() {
        Collection<Admin> admins = new ArrayList<>();
        admins.add(new Admin(1,4));
        Assert.assertEquals("Must return one Admin", admins, adminService.getAllAdmin());
        Admin admin = new Admin(2, 5);
        Person person = new Person();
        person.setName("Maxim");
        person.setSurname("Rahmanenko");
        person.setPassportId(52352);
        person.setPhoneNumber(4252);
        person.setRole(Role.ADMIN);
        adminService.addAdmin(person);
        admins.add(admin);
        Assert.assertEquals(admins, adminService.getAllAdmin());
    }
}