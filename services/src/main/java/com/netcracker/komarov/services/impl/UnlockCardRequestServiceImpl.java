package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.UnlockCardRequest;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.UnlockCardRequestRepository;
import com.netcracker.komarov.services.interfaces.UnlockCardRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class UnlockCardRequestServiceImpl implements UnlockCardRequestService {
    private UnlockCardRequestRepository unlockCardRequestRepository;
    private Logger logger = LoggerFactory.getLogger(UnlockCardRequestServiceImpl.class);

    @Autowired
    public UnlockCardRequestServiceImpl(RepositoryFactory RepositoryFactory) {
        this.unlockCardRequestRepository = RepositoryFactory.getUnlockCardRequestRepository();
    }

    @Transactional
    @Override
    public Collection<UnlockCardRequest> getAllCardRequest() {
        logger.info("Return all requests to unlock cards");
        return unlockCardRequestRepository.findAll();
    }

    @Transactional
    @Override
    public UnlockCardRequest unlockCardRequest(long cardId) {
        UnlockCardRequest temp = null;
        if (unlockCardRequestRepository.longByCardId(cardId) == null) {
            UnlockCardRequest request = new UnlockCardRequest();
            request.getCard().setId(cardId);
            temp = unlockCardRequestRepository.save(request);
        }
        logger.info("Card was unlocked");
        return temp;
    }
}
