package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.UnlockAccountRequest;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.UnlockAccountRequestRepository;
import com.netcracker.komarov.services.interfaces.UnlockAccountRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class UnlockAccountRequestServiceImpl implements UnlockAccountRequestService {
    private UnlockAccountRequestRepository unlockAccountRequestRepository;
    private Logger logger = LoggerFactory.getLogger(UnlockAccountRequestServiceImpl.class);

    @Autowired
    public UnlockAccountRequestServiceImpl(RepositoryFactory repositoryFactory) {
        this.unlockAccountRequestRepository = repositoryFactory.getUnlockAccountRequestRepository();
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
        if (unlockAccountRequestRepository.findUnlockAccountRequestByAccountId(accountId) == null) {
            UnlockAccountRequest request = new UnlockAccountRequest();
            request.getAccount().setId(accountId);
            temp = unlockAccountRequestRepository.save(request);
            logger.info("Account was unlocked");
        } else {
            logger.info("You had already sent request to unlock account");
        }
        return temp;
    }
}
