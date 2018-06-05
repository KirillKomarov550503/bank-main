package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Card;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.CardRepository;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.dto.converter.CardConverter;
import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.feign.RequestFeignClient;
import com.netcracker.komarov.services.interfaces.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application-error.properties")
public class CardServiceImpl implements CardService {
    private CardRepository cardRepository;
    private AccountRepository accountRepository;
    private CardConverter cardConverter;
    private PersonRepository personRepository;
    private PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceImpl.class);
    private Environment environment;
    private RequestFeignClient requestFeignClient;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, AccountRepository accountRepository,
                           CardConverter cardConverter, PersonRepository personRepository,
                           PasswordEncoder passwordEncoder, Environment environment,
                           RequestFeignClient requestFeignClient) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
        this.cardConverter = cardConverter;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.environment = environment;
        this.requestFeignClient = requestFeignClient;
    }

    private Collection<CardDTO> convertCollection(Collection<Card> cards) {
        return cards.stream()
                .map(card -> cardConverter.convertToDTO(card))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isContain(long accountId, long cardId) throws NotFoundException {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        boolean contain;
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            contain = card.getAccount().getId() == accountId;
        } else {
            String error = environment.getProperty("error.card.search") + cardId;
            LOGGER.error(error);
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
                LOGGER.error(error);
                throw new LogicException(error);
            } else {
                card.setLocked(true);
                LOGGER.info("Card with ID " + cardId + " was locked");
                temp = cardRepository.save(card);
            }
        } else {
            String error = environment.getProperty("error.card.search") + cardId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return cardConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public CardDTO save(CardDTO cardDTO) throws NotFoundException, LogicException {
        Card card = cardConverter.convertToEntity(cardDTO);
        Optional<Account> optionalAccount = accountRepository.findById(cardDTO.getAccountId());
        Card temp;
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.isLocked()) {
                String error = "Account with ID " + account.getId() + " is locked";
                LOGGER.error(error);
                throw new LogicException(error);
            }
            card.setAccount(account);
            card.setLocked(false);
            String pin = card.getPin();
            card.setPin(passwordEncoder.encode(pin));
            account.getCards().add(card);
            temp = cardRepository.save(card);
            LOGGER.info("Creation of new card with ID " + temp.getId());
        } else {
            String error = environment.getProperty("error.card.search") + cardDTO.getAccountId();
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return cardConverter.convertToDTO(temp);
    }

    @Transactional
    @Override
    public Collection<CardDTO> findCardsByClientIdAndLock(long personId, boolean lock) throws NotFoundException {
        Optional<Person> optionalClient = personRepository.findById(personId);
        Collection<Card> cards;
        if (optionalClient.isPresent()) {
            cards = cardRepository.findCardsByPersonIdAndLocked(personId, lock);
            LOGGER.info("Return all unlocked cards by client ID " + personId);
        } else {
            String error = environment.getProperty("error.card.search") + personId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return convertCollection(cards);
    }

    @Transactional
    @Override
    public Collection<CardDTO> findCardsByAccountId(long accountId) throws NotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        Collection<Card> cards;
        if (optionalAccount.isPresent()) {
            LOGGER.info("Return all cards that connected with account with ID " + accountId);
            cards = cardRepository.findCardsByAccountId(accountId);
        } else {
            String error = environment.getProperty("error.card.search") + accountId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return convertCollection(cards);
    }

    @Transactional
    @Override
    public CardDTO unlockCard(long cardId) throws LogicException, NotFoundException {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        Card res;
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            if (card.isLocked()) {
                card.setLocked(false);
                res = cardRepository.save(card);
                LOGGER.info("Successful unlock card with ID: " + card.getId());
            } else {
                String error = "This card is already unlocked";
                LOGGER.error(error);
                throw new LogicException(error);
            }
        } else {
            String error = environment.getProperty("error.card.search") + cardId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return cardConverter.convertToDTO(res);
    }

    @Transactional
    @Override
    public Collection<CardDTO> findAllCards() {
        LOGGER.info("Return all cards");
        return convertCollection(cardRepository.findAll());
    }

    @Transactional
    @Override
    public void deleteById(long cardId) throws NotFoundException {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            requestFeignClient.deleteById(cardId, "CARD");
            cardRepository.deleteById(cardId);
            LOGGER.info("Card with ID " + cardId + " was deleted");
        } else {
            String error = environment.getProperty("error.card.search") + cardId;
            LOGGER.error(error);
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
            LOGGER.info("Return card with ID " + cardId);
        } else {
            String error = environment.getProperty("error.card.search") + cardId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return cardConverter.convertToDTO(card);
    }
}
