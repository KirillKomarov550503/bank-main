package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.ClientDAO;
import dev3.bank.entity.Account;
import dev3.bank.entity.Client;

import java.util.Collection;

public class ClientDAOImpl extends CrudDAOImpl<Client> implements ClientDAO {
    public ClientDAOImpl() {
        super(Client.class);
    }


}
