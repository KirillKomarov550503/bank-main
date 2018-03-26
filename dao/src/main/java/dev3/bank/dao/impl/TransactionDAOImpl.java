package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.TransactionDAO;
import dev3.bank.entity.Transaction;

import java.util.Collection;
import java.util.stream.Collectors;

public class TransactionDAOImpl extends CrudDAOImpl<Transaction> implements TransactionDAO {
    public TransactionDAOImpl() {
        super(Transaction.class);
    }

    @Override
    public Collection<Transaction> getByAccountFromId(long accountFromId) {
        return getEntityMapValues()
                .stream()
                .filter(transaction -> transaction.getAccountFrom().getId() == accountFromId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Transaction> getByAccountToId(long accountToId) {
        return getEntityMapValues()
                .stream()
                .filter(transaction -> transaction.getAccountTo().getId() == accountToId)
                .collect(Collectors.toList());
    }
}
