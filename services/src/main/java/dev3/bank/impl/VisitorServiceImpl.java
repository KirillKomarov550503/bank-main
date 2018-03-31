package dev3.bank.impl;

import dev3.bank.dao.impl.ClientDAOImpl;
import dev3.bank.dao.impl.GeneralNewsDAOImpl;
import dev3.bank.dao.interfaces.ClientDAO;
import dev3.bank.dao.interfaces.GeneralNewsDAO;
import dev3.bank.entity.Client;
import dev3.bank.entity.GeneralNews;
import dev3.bank.entity.Person;
import dev3.bank.interfaces.VisitorService;

import java.util.Collection;

public class VisitorServiceImpl implements VisitorService {
    @Override
    public Collection<GeneralNews> getAllNews() {
        GeneralNewsDAO generalNewsDAO = new GeneralNewsDAOImpl();
        return generalNewsDAO.getAll();
    }

    @Override
    public Person registration(Client client) {
        ClientDAO clientDAO = new ClientDAOImpl();
        return clientDAO.add(client);
    }

    @Override
    public Person signIn(Person person) {
        return null;
    }
}
