package dev3.bank.dao;

import dev3.bank.entity.Transaction;

import java.util.Collection;

public interface TransactionDAO extends CrudDAO<Transaction> {
    Collection<Transaction> getByAccountFromId(long accountFromId);
    Collection<Transaction> getByAccountToId(long accountToId);
}
