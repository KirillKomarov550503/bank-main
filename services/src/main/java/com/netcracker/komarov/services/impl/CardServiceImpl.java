package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Card;
import com.netcracker.komarov.dao.entity.UnlockCardRequest;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.CardRepository;
import com.netcracker.komarov.dao.repository.UnlockCardRequestRepository;
import com.netcracker.komarov.services.interfaces.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {
    private UnlockCardRequestRepository unlockCardRequestRepository;
    private CardRepository cardRepository;
    private AccountRepository accountRepository;
    private Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    @Autowired
    public CardServiceImpl(RepositoryFactory repositoryFactory) {
        this.unlockCardRequestRepository = repositoryFactory.getUnlockCardRequestRepository();
        this.cardRepository = repositoryFactory.getCardRepository();
        this.accountRepository = repositoryFactory.getAccountRepository();
    }

    @Transactional
    @Override
    public Card lockCard(long cardId) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        Card card = null;
        Card temp = null;
        if (cardOptional.isPresent()) {
            card = cardOptional.get();
            card.setLocked(true);
            logger.info("Card was locked");
            temp = cardRepository.save(card);
        } else {
            logger.info("There is no such card in database");
        }
        return temp;
    }

    @Transactional
    @Override
    public Card createCard(Card card, long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        Card temp = null;
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            card.setAccount(account);
            account.getCards().add(card);
            temp = cardRepository.save(card);
            logger.info("Creation of new card");
        } else {
            logger.info("There is no such account in database");
        }
        return temp;
    }

    @Transactional
    @Override
    public Collection<Card> getLockCards(long clientId) {
        logger.info("Return all locked cards by client ID");
        return cardRepository.findCardsByClientIdAndLocked(clientId, true);
    }

    @Transactional
    @Override
    public Collection<Card> getUnlockCards(long clientId) {
        logger.info("Return all unlocked cards by client ID");
        return cardRepository.findCardsByClientIdAndLocked(clientId, false);
    }

    @Transactional
    @Override
    public Collection<Card> getAllCardsByAccount(long accountId) {
        logger.info("Return all cards that connected with account");
        return cardRepository.findCardsByAccountId(accountId);
    }

    @Transactional
    @Override
    public Collection<Card> getAllUnlockCardRequest() {
        Collection<Card> cards = new ArrayList<>();
        Collection<UnlockCardRequest> requests = unlockCardRequestRepository.findAll();
        for (UnlockCardRequest request : requests) {
            cards.add(cardRepository.findById(request.getCard().getId()).get());
        }
        if (cards.size() == 0) {
            logger.info("There is no such requests to unlock card");
        } else {
            logger.info("Return all request to unlock card");
        }
        return cards;
    }

    @Transactional
    @Override
    public void unlockCard(long cardId) {
        Optional<UnlockCardRequest> optionalRequest = unlockCardRequestRepository.findAll()
                .stream()
                .filter(unlockCardRequest -> unlockCardRequest.getCard().getId() == cardId)
                .findFirst();
        if (optionalRequest.isPresent()) {
            Optional<Card> optionalCard = cardRepository.findById(cardId);
            if (optionalCard.isPresent()) {
                Card card = optionalCard.get();
                card.setLocked(false);
                cardRepository.save(card);
                unlockCardRequestRepository.deleteByCardId(cardId);
                logger.info("Card was locked");
            }
        } else {
            logger.info("There is no such card in requests");
        }
    }

    @Transactional
    @Override
    public Collection<Card> getAllCards() {
        logger.info("Return all cards");
        return cardRepository.findAll();
    }
}
