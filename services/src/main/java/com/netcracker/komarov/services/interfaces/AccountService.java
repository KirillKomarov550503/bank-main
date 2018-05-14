package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;

import java.util.Collection;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO, long clientId) throws NotFoundException;

    AccountDTO lockAccount(long accountId) throws LogicException, NotFoundException;

    Collection<AccountDTO> getAccountsByClientIdAndLock(long clientId, boolean lock) throws NotFoundException;

    AccountDTO refill(long accountId) throws LogicException, NotFoundException;

    Collection<AccountDTO> getAllAccounts();

    AccountDTO unlockAccount(long accountId) throws LogicException, NotFoundException;

    void deleteById(long accountId) throws NotFoundException;

    AccountDTO findById(long accountId) throws NotFoundException;
}

