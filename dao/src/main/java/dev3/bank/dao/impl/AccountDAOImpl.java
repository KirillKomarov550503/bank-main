package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.AccountDAO;
import dev3.bank.entity.Account;

import java.util.Collection;
import java.util.stream.Collectors;

public class AccountDAOImpl extends CrudDAOImpl<Account> implements AccountDAO {
    public AccountDAOImpl() {
        super(Account.class);
    }

    @Override
    public Collection<Account> getAccountsByClientId(long clientId) {
        return getEntityMapValues()
                .stream()
                .filter(account -> account.getClient().getId() == clientId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Account> getLockedAccounts() {
        return getEntityMapValues()
                .stream()
                .filter(Account::isLocked)
                .collect(Collectors.toList());

    }

    @Override
    public Collection<Account> getUnlockedAccounts() {
        return getEntityMapValues()
                .stream()
                .filter(account -> !account.isLocked())
                .collect(Collectors.toList());
    }
}
