package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Card;
import com.netcracker.komarov.dao.entity.Request;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.CardRepository;
import com.netcracker.komarov.dao.repository.RequestRepository;
import com.netcracker.komarov.services.dto.converter.CardConverter;
import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.interfaces.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    private RequestRepository requestRepository;
    private CardRepository cardRepository;
    private AccountRepository accountRepository;
    private CardConverter cardConverter;
    private Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    @Autowired
    public CardServiceImpl(RepositoryFactory repositoryFactory, CardConverter cardConverter) {
        this.requestRepository = repositoryFactory.getRequestRepository();
        this.cardRepository = repositoryFactory.getCardRepository();
        this.accountRepository = repositoryFactory.getAccountRepository();
        this.cardConverter = cardConverter;
    }

    private Collection<CardDTO> convertCollection(Collection<Card> cards) {
        return cards.stream()
                .map(card -> cardConverter.convertToDTO(card))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CardDTO lockCard(long cardId) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        Card card;
        Card temp = null;
        if (cardOptional.isPresent()) {
            card = cardOptional.get();
            card.setLocked(true);
            logger.info("Card was locked");
            temp = cardRepository.save(card);
        } else {
            logger.error("There is no such card in database");
        }
        return cardConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public CardDTO createCard(CardDTO cardDTO) {
        Card card = cardConverter.convertToEntity(cardDTO);
        Optional<Account> optionalAccount = accountRepository.findById(cardDTO.getAccountId());
        Card temp = null;
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            card.setAccount(account);
            card.setLocked(false);
            account.getCards().add(card);
            temp = cardRepository.save(card);
            logger.info("Creation of new card");
        } else {
            logger.error("There is no such account in database");
        }
        return cardConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public Collection<CardDTO> getCardsByClientIdAndLock(long clientId, boolean lock) {
        logger.info("Return all unlocked cards by client ID");
        return convertCollection(cardRepository.findCardsByClientIdAndLocked(clientId, lock));
    }

    @Transactional
    @Override
    public Collection<CardDTO> getAllCardsByAccountId(long accountId) {
        logger.info("Return all cards that connected with account");
        return convertCollection(cardRepository.findCardsByAccountId(accountId));
    }

    @Transactional
    @Override
    public CardDTO unlockCard(long cardId) {
        Optional<Request> optionalRequest = requestRepository.findAll()
                .stream()
                .filter(request -> request.getCard() != null)
                .filter(request -> request.getCard().getId() == cardId)
                .findFirst();
        Card res = null;
        if (optionalRequest.isPresent()) {
            Card card = optionalRequest.get().getCard();
            card.setLocked(false);
            res = cardRepository.save(card);
            requestRepository.deleteRequestById(optionalRequest.get().getId());
            logger.info("Card was unlocked");
        } else {
            logger.error("There is no such card in requests");
        }
        return cardConverter.convertToDTO(res);
    }

    @Transactional
    @Override
    public Collection<CardDTO> getAllCards() {
        logger.info("Return all cards");
        return convertCollection(cardRepository.findAll());
    }

    @Transactional
    @Override
    public void deleteById(long cardId) {
        cardRepository.deleteById(cardId);
        logger.info("Card was deleted");
    }

    @Transactional
    @Override
    public CardDTO findById(long cardId) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        Card card = null;
        if (optionalCard.isPresent()) {
            card = optionalCard.get();
            logger.info("Return card");
        } else {
            logger.error("There is no such card");
        }
        return cardConverter.convertToDTO(card);
    }
}
