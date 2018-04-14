package dev3.bank.impl;

import dev3.bank.dao.interfaces.ClientDAO;
import dev3.bank.dao.interfaces.NewsDAO;
import dev3.bank.dao.interfaces.PersonDAO;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.Client;
import dev3.bank.entity.News;
import dev3.bank.entity.NewsStatus;
import dev3.bank.entity.Person;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.VisitorService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class VisitorServiceImpl implements VisitorService {
    private static VisitorServiceImpl visitorService;
    private NewsDAO newsDAO;
    private ClientDAO clientDAO;
    private PersonDAO personDAO;

    private VisitorServiceImpl() {
    }

    public static synchronized VisitorServiceImpl getVisitorService() {
        if (visitorService == null) {
            visitorService = new VisitorServiceImpl();
        }
        return visitorService;
    }

    @Override
    public void setDAO(DAOFactory daoFactory) {
        clientDAO = daoFactory.getClientDAO();
        newsDAO = daoFactory.getNewsDAO();
        personDAO = daoFactory.getPersonDAO();
    }

    @Override
    public Collection<News> getAllNews() {
        Collection<News> temp = null;
        try {
            temp = newsDAO.getNewsByStatus(NewsStatus.GENERAL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Person registration(Person person) {
        Connection connection = DataBase.getConnection();
        Client addRes = null;
        try {
            connection.setAutoCommit(false);
            Client client = new Client();
            client.setPersonId(personDAO.add(person).getId());
            addRes = clientDAO.add(client);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                System.out.println("ROLLBACK");
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                System.out.println("SQL exception");
                e1.printStackTrace();
            }
            System.out.println("SQL exception");
            e.printStackTrace();
        }

        return addRes;
    }

    @Override
    public Person signIn(Person person) {
        return null;
    }
}
