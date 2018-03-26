package dev3.bank.dao.interfaces;

import dev3.bank.entity.Account;

import java.util.Collection;

public interface AccountDAO extends CrudDAO<Account> {
    Collection<Account> getLockedAccounts();
    Collection<Account> getUnlockedAccounts();
}
