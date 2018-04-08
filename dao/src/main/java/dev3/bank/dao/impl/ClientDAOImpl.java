package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.ClientDAO;
import dev3.bank.entity.Client;

public class ClientDAOImpl extends CrudDAOImpl<Client> implements ClientDAO {
    public ClientDAOImpl() {
        super(Client.class);
    }
}