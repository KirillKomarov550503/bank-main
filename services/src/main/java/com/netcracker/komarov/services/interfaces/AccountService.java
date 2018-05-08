package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.AccountDTO;

import java.util.Collection;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO, long clientId);

    AccountDTO lockAccount(long accountId);

    Collection<AccountDTO> getAccountsByClientIdAndLock(long clientId, boolean lock);

    AccountDTO refill(long accountId);

    Collection<AccountDTO> getAllAccounts();

    AccountDTO unlockAccount(long accountId);

    void deleteById(long accountId);

    AccountDTO findById(long accountId);
}

