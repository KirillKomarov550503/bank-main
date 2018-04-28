package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Card;
import com.netcracker.komarov.dao.entity.UnlockCardRequest;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.CardRepository;
import com.netcracker.komarov.dao.repository.UnlockCardRequestRepository;
import com.netcracker.komarov.services.interfaces.UnlockCardRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class UnlockCardRequestServiceImpl implements UnlockCardRequestService {
    private UnlockCardRequestRepository unlockCardRequestRepository;
    private CardRepository cardRepository;
    private Logger logger = LoggerFactory.getLogger(UnlockCardRequestServiceImpl.class);

    @Autowired
    public UnlockCardRequestServiceImpl(RepositoryFactory repositoryFactory) {
        this.unlockCardRequestRepository = repositoryFactory.getUnlockCardRequestRepository();
        this.cardRepository = repositoryFactory.getCardRepository();
    }

    @Transactional
    @Override
    public Collection<UnlockCardRequest> getAllCardRequest() {
        logger.info("Return all requests to unlock cards");
        return unlockCardRequestRepository.findAll();
    }

    @Transactional
    @Override
    public UnlockCardRequest addCardRequest(long cardId) {
        UnlockCardRequest temp = null;
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if(optionalCard.isPresent()){
            if (unlockCardRequestRepository.longByCardId(cardId) == null) {
                Card card = optionalCard.get();
                UnlockCardRequest request = new UnlockCardRequest();
                request.setCard(card);
                card.setUnlockCardRequest(request);
                temp = unlockCardRequestRepository.save(request);
                logger.info("Sending request to unlock card");
            } else {
                logger.info("You had already sent request to unlock card");
            }
        } else {
            logger.info("There is no such card in database");
        }
        return temp;
    }
}
