package dev3.bank.dao.interfaces;

import dev3.bank.entity.Client;

import java.util.Collection;

public interface ClientDAO extends CrudDAO<Client> {
    Client getByAccountId(long accountId);

}
