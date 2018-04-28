package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.UnlockAccountRequest;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.UnlockAccountRequestRepository;
import com.netcracker.komarov.services.interfaces.UnlockAccountRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class UnlockAccountRequestServiceImpl implements UnlockAccountRequestService {
    private UnlockAccountRequestRepository unlockAccountRequestRepository;
    private AccountRepository accountRepository;
    private Logger logger = LoggerFactory.getLogger(UnlockAccountRequestServiceImpl.class);

    @Autowired
    public UnlockAccountRequestServiceImpl(RepositoryFactory repositoryFactory) {
        this.unlockAccountRequestRepository = repositoryFactory.getUnlockAccountRequestRepository();
        this.accountRepository = repositoryFactory.getAccountRepository();
    }

    @Transactional
    @Override
    public Collection<UnlockAccountRequest> getAllAccountRequest() {
        logger.info("Return all request to unlock account");
        return unlockAccountRequestRepository.findAll();
    }

    @Transactional
    @Override
    public UnlockAccountRequest addAccountRequest(long accountId) {
        UnlockAccountRequest temp = null;
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if(optionalAccount.isPresent()){
            if (unlockAccountRequestRepository.findUnlockAccountRequestByAccountId(accountId) == null) {
                UnlockAccountRequest request = new UnlockAccountRequest();
                Account account = optionalAccount.get();
                request.setAccount(account);
                account.setUnlockAccountRequest(request);
                temp = unlockAccountRequestRepository.save(request);
                logger.info("Sending request to unlock account");
            } else {
                logger.info("You had already sent request to unlock account");
            }
        } else {
            logger.info("There is no such account in database");
        }
        return temp;
    }
}
