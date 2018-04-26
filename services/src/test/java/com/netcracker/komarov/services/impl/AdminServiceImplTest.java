package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Admin;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.dao.utils.DataBase;
import com.netcracker.komarov.services.interfaces.AdminService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;


public class AdminServiceImplTest extends AbstractSpringTest {

    @Autowired
    private AdminService adminService;

    @Before
    public void init() {
        DataBase.initTable();
        DataBase.insertValues();
    }

    @After
    public void destroy() {
        DataBase.dropTable();
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
        admins.add(new Admin(1, 4));
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