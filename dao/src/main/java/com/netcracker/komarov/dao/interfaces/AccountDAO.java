package com.netcracker.komarov.dao.interfaces;

import com.netcracker.komarov.dao.entity.Account;

import java.sql.SQLException;
import java.util.Collection;

public interface AccountDAO extends CrudDAO<Account> {
    Collection<Account> getLockedAccounts() throws SQLException;
    Collection<Account> getUnlockedAccounts() throws SQLException;
    Collection<Account> getLockedAccountsByClientId(long clientId) throws SQLException;
    Collection<Account> getUnlockedAccountsByClientId(long clientId) throws SQLException;
    Collection<Account> getAccountsByClientId(long clientId) throws SQLException;
}
