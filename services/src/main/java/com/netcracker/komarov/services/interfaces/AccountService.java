package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;

import java.util.Collection;

public interface AccountService {
    AccountDTO saveAccount(AccountDTO accountDTO, long personId) throws NotFoundException;

    AccountDTO lockAccount(long accountId) throws LogicException, NotFoundException;

    Collection<AccountDTO> findAccountsByClientIdAndLock(long personId, boolean lock) throws NotFoundException;

    AccountDTO refillAccount(long accountId) throws LogicException, NotFoundException;

    Collection<AccountDTO> findAllAdmins();

    AccountDTO unlockAccount(long accountId) throws LogicException, NotFoundException;

    void deleteById(long accountId) throws NotFoundException;

    AccountDTO findById(long accountId) throws NotFoundException;

    boolean isContain(long personId, long accountId) throws NotFoundException;
}

