package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.AccountDTO;

import java.util.Collection;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO, long clientId);

    AccountDTO lockAccount(long accountId);

    Collection<AccountDTO> getLockAccounts(long clientId);

    Collection<AccountDTO> getUnlockAccounts(long clientId);

    AccountDTO refill(long accountId);

    Collection<AccountDTO> getAllUnlockAccountRequest();

    Collection<AccountDTO> getAllAccounts();

    void unlockAccount(long accountId);
}

