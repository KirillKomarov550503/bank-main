package dev3.bank.impl;

import dev3.bank.dao.impl.ClientDAOImpl;
import dev3.bank.dao.interfaces.ClientDAO;
import dev3.bank.entity.Client;
import dev3.bank.interfaces.TestService;

import java.util.Collection;

public class TestServiceImpl implements TestService {
    @Override
    public Collection<Client> getAll() {
        ClientDAO clientDAO = new ClientDAOImpl();
        return clientDAO.getAll();
    }
}
