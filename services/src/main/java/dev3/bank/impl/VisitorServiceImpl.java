package dev3.bank.impl;

import dev3.bank.dao.impl.ClientDAOImpl;
import dev3.bank.dao.impl.NewsDAOImpl;
import dev3.bank.dao.interfaces.ClientDAO;
import dev3.bank.dao.interfaces.NewsDAO;
import dev3.bank.entity.Client;
import dev3.bank.entity.News;
import dev3.bank.entity.NewsStatus;
import dev3.bank.entity.Person;
import dev3.bank.interfaces.VisitorService;

import java.util.Collection;

public class VisitorServiceImpl implements VisitorService {
    private static VisitorServiceImpl visitorService;
    private NewsDAO newsDAO;
    private ClientDAO clientDAO;

    private VisitorServiceImpl() {
    }

    public static synchronized VisitorServiceImpl getVisitorService() {
        if (visitorService == null) {
            visitorService = new VisitorServiceImpl();
        }
        return visitorService;
    }

    public void setNewsDAO(NewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }

    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public Collection<News> getAllNews() {
        return newsDAO.getNewsByStatus(NewsStatus.GENERAL);
    }

    @Override
    public Person registration(Client client) {
        return clientDAO.add(client);
    }

    @Override
    public Person signIn(Person person) {
        return null;
    }
}
