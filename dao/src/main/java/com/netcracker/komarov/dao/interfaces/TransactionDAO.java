package com.netcracker.komarov.dao.interfaces;

import com.netcracker.komarov.dao.entity.Transaction;

import java.sql.SQLException;
import java.util.Collection;

public interface TransactionDAO extends CrudDAO<Transaction> {
    Collection<Transaction> getByAccountFromId(long accountFromId) throws SQLException;

    Collection<Transaction> getByAccountToId(long accountToId) throws SQLException;

    Collection<Transaction> getByClientId(long clientId) throws SQLException;
}
