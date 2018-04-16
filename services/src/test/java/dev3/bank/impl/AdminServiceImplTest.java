package dev3.bank.impl;


import dev3.bank.dao.impl.PersonDAOImpl;
import dev3.bank.dao.interfaces.PersonDAO;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.BaseEntity;
import dev3.bank.entity.Client;
import dev3.bank.entity.Person;
import dev3.bank.entity.Role;
import dev3.bank.factory.DAOFactory;
import dev3.bank.factory.PostgreSQLDAOFactory;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminServiceImplTest {

    private AdminServiceImpl adminService;
    private Connection connection;
    private List<? extends BaseEntity> list;
    @Before
    public void initService() {
        DataBase.executeProperty("drop.table.path");
        DataBase.executeProperty("init.table.path");
        connection = DataBase.getConnection();
        DAOFactory daoFactory = PostgreSQLDAOFactory.getPostgreSQLDAOFactory();
        adminService = AdminServiceImpl.getAdminService();
        adminService.setDAO(daoFactory);
    }


    @Test
    public void getAllClients() {
        Person person1 = new Person();
        person1.setName("Vladimir");
        person1.setSurname("Putin");
        person1.setPassportId(1L);
        person1.setPhoneNumber(111111);
        person1.setRole(Role.CLIENT);
        Person person2 = new Person();
        person2.setName("Dmitriy");
        person2.setName("Medvedev");
        person2.setPassportId(2L);
        person2.setPhoneNumber(2);
        person2.setRole(Role.CLIENT);
        Person person3 = new Person();
        person3.setName("Vladimir");
        person3.setSurname("Lenin");
        person3.setPassportId(3L);
        person3.setPhoneNumber(0);
        person3.setRole(Role.CLIENT);
        PersonDAO personDAO = PersonDAOImpl.getPersonDAO(connection);
        try {
            person1 = personDAO.add(person1);
            person2 = personDAO.add(person2);
            person3 = personDAO.add(person3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Client client = new Client();
    }

    @Test
    public void getAllUnlockCardRequest() {
    }

    @Test
    public void getAllUnlockAccountRequest() {
    }

    @Test
    public void unlockCard() {
    }

    @Test
    public void unlockAccount() {
    }

    @Test
    public void getAllAccounts() {
    }

    @Test
    public void getAllCards() {
    }

    @Test
    public void getAllGeneralNews() {
    }

    @Test
    public void getAllClientNews() {
    }

    @Test
    public void addClientNews() {
    }

    @Test
    public void addGeneralNews() {
    }

    @Test
    public void getAllAccountRequest() {
    }

    @Test
    public void getAllCardRequest() {
    }

    @Test
    public void addAdmin() {
    }

    @Test
    public void getAllAdmin() {
    }

    @Test
    public void getAllNewsByStatus() {
    }
}