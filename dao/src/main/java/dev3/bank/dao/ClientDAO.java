package dev3.bank.dao;

import dev3.bank.entity.Client;

import java.util.Collection;

public interface ClientDAO extends CrudDAO<Client> {
    Collection<Client> getByAccountId(long accountId);
}
