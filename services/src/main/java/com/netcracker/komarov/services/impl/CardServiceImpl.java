package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Card;
import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.Request;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.CardRepository;
import com.netcracker.komarov.dao.repository.ClientRepository;
import com.netcracker.komarov.dao.repository.RequestRepository;
import com.netcracker.komarov.services.dto.converter.CardConverter;
import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
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
    private ClientRepository clientRepository;
    private Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    @Autowired
    public CardServiceImpl(RequestRepository requestRepository, CardRepository cardRepository,
                           AccountRepository accountRepository, CardConverter cardConverter,
                           ClientRepository clientRepository) {
        this.requestRepository = requestRepository;
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
        this.cardConverter = cardConverter;
        this.clientRepository = clientRepository;
    }

    private Collection<CardDTO> convertCollection(Collection<Card> cards) {
        return cards.stream()
                .map(card -> cardConverter.convertToDTO(card))
                .collect(Collectors.toList());
    }

    @Override
    public boolean contain(long accountId, long cardId) throws NotFoundException {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        boolean contain;
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            contain = card.getAccount().getId() == accountId;
        } else {
            String error = "No such card";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return contain;
    }

    @Transactional
    @Override
    public CardDTO lockCard(long cardId) throws LogicException, NotFoundException {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        Card temp;
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            if (card.isLocked()) {
                String error = "This card is already locked";
                logger.error(error);
                throw new LogicException(error);
            } else {
                card.setLocked(true);
                logger.info("Card was locked");
                temp = cardRepository.save(card);
            }
        } else {
            String error = "There is no such card in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return cardConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public CardDTO createCard(CardDTO cardDTO) throws NotFoundException {
        Card card = cardConverter.convertToEntity(cardDTO);
        Optional<Account> optionalAccount = accountRepository.findById(cardDTO.getAccountId());
        Card temp;
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            card.setAccount(account);
            card.setLocked(false);
            account.getCards().add(card);
            temp = cardRepository.save(card);
            logger.info("Creation of new card");
        } else {
            String error = "There is no such account in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return cardConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public Collection<CardDTO> getCardsByClientIdAndLock(long clientId, boolean lock) throws NotFoundException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Collection<Card> cards;
        if (optionalClient.isPresent()) {
            cards = cardRepository.findCardsByClientIdAndLocked(clientId, lock);
            logger.info("Return all unlocked cards by client ID");
        } else {
            String error = "There is no such client in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return convertCollection(cards);
    }

    @Transactional
    @Override
    public Collection<CardDTO> getAllCardsByAccountId(long accountId) throws NotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        Collection<Card> cards;
        if (optionalAccount.isPresent()) {
            logger.info("Return all cards that connected with account");
            cards = cardRepository.findCardsByAccountId(accountId);
        } else {
            String error = "There is no such account in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return convertCollection(cards);
    }

    @Transactional
    @Override
    public CardDTO unlockCard(long cardId) throws LogicException, NotFoundException {
        Optional<Request> optionalRequest = requestRepository.findAll()
                .stream()
                .filter(request -> request.getCard() != null)
                .filter(request -> request.getCard().getId() == cardId)
                .findFirst();
        Card res;
        if (optionalRequest.isPresent()) {
            Card card = optionalRequest.get().getCard();
            if (card.isLocked()) {
                card.setLocked(false);
                res = cardRepository.save(card);
                requestRepository.deleteRequestById(optionalRequest.get().getId());
                logger.info("Card was unlocked");
            } else {
                String error = "This account is already unlocked";
                logger.error(error);
                throw new LogicException(error);
            }
        } else {
            String error = "There is no such card in requests";
            logger.error(error);
            throw new NotFoundException(error);
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
    public void deleteById(long cardId) throws NotFoundException {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            cardRepository.deleteById(cardId);
            logger.info("Card was deleted");
        } else {
            String error = "There is no such card";
            logger.error(error);
            throw new NotFoundException(error);
        }
    }

    @Transactional
    @Override
    public CardDTO findById(long cardId) throws NotFoundException {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        Card card;
        if (optionalCard.isPresent()) {
            card = optionalCard.get();
            logger.info("Return card");
        } else {
            String error = "There is no such card";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return cardConverter.convertToDTO(card);
    }
}
