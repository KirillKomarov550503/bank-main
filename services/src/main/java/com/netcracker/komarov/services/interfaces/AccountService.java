package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.Account;

import java.util.Collection;

public interface AccountService {
    Account createAccount(Account account, long clientId);

    Account lockAccount(long accountId);

    Collection<Account> getLockAccounts(long clientId);

    Collection<Account> getUnlockAccounts(long clientId);

    Account refill(long accountId);

    Collection<Account> getAllUnlockAccountRequest();

    Collection<Account> getAllAccounts();

    void unlockAccount(long accountId);
}
