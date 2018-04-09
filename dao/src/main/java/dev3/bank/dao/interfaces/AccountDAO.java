package dev3.bank.dao.interfaces;

import dev3.bank.entity.Account;

import java.util.Collection;

public interface AccountDAO extends CrudDAO<Account> {
    Collection<Account> getLockedAccounts();
    Collection<Account> getUnlockedAccounts();
    Collection<Account> getLockedAccountsByClientId(long clientId);
    Collection<Account> getUnlockedAccountsByClientId(long clientId);
    Collection<Account> getAccountsByClientId(long clientId);
    Account getByAccountId(long accountId);
}
