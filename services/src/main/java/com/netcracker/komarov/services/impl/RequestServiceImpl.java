package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Card;
import com.netcracker.komarov.dao.entity.Request;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.AccountRepository;
import com.netcracker.komarov.dao.repository.CardRepository;
import com.netcracker.komarov.dao.repository.RequestRepository;
import com.netcracker.komarov.services.dto.RequestStatus;
import com.netcracker.komarov.services.dto.converter.RequestConverter;
import com.netcracker.komarov.services.dto.entity.RequestDTO;
import com.netcracker.komarov.services.interfaces.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {
    private RequestRepository requestRepository;
    private AccountRepository accountRepository;
    private CardRepository cardRepository;
    private RequestConverter converter;
    private Logger logger = LoggerFactory.getLogger(RequestServiceImpl.class);

    @Autowired
    public RequestServiceImpl(RepositoryFactory repositoryFactory, RequestConverter requestConverter) {
        this.requestRepository = repositoryFactory.getRequestRepository();
        this.accountRepository = repositoryFactory.getAccountRepository();
        this.cardRepository = repositoryFactory.getCardRepository();
        this.converter = requestConverter;
    }

    private Collection<RequestDTO> convertCollection(Collection<Request> requests) {
        return requests.stream()
                .map(request -> converter.convertToDTO(request))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RequestDTO saveRequest(long requestId, RequestStatus requestStatus) {
        Request request = new Request();
        Request res = null;
        if (requestStatus.equals(RequestStatus.ACCOUNT)) {
            request.setCard(null);
            Optional<Request> optionalRequest = requestRepository.findAll()
                    .stream()
                    .filter(elem -> elem.getAccount() != null)
                    .filter(elem -> elem.getAccount().getId() == requestId)
                    .findFirst();
            if (optionalRequest.isPresent()) {
                logger.info("This account have already added to requests");
            } else {
                Optional<Account> optionalAccount = accountRepository.findById(requestId);
                if (optionalAccount.isPresent()) {
                    Account account = optionalAccount.get();
                    account.setRequest(request);
                    request.setAccount(account);
                    res = requestRepository.save(request);
                    logger.info("Add request to unlock account");
                } else {
                    logger.info("There is no such account in database");
                }
            }
        } else {
            if (requestStatus.equals(RequestStatus.CARD)) {
                request.setAccount(null);
                Optional<Request> optionalRequest = requestRepository.findAll()
                        .stream()
                        .filter(elem -> elem.getCard() != null)
                        .filter(elem -> elem.getCard().getId() == requestId)
                        .findFirst();
                if (optionalRequest.isPresent()) {
                    logger.info("This card have already added to requests");
                } else {
                    Optional<Card> optionalCard = cardRepository.findById(requestId);
                    if (optionalCard.isPresent()) {
                        Card card = optionalCard.get();
                        card.setRequest(request);
                        request.setCard(card);
                        res = requestRepository.save(request);
                        logger.info("Add request to unlock card");
                    } else {
                        logger.info("There is no such card in database");
                    }
                }
            }
        }
        return converter.convertToDTO(res);
    }

    @Transactional
    @Override
    public Collection<RequestDTO> findAllRequests() {
        logger.info("Return all requests");
        return convertCollection(requestRepository.findAll());
    }

    @Transactional
    @Override
    public RequestDTO findById(long id) {
        Request request = null;
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            request = optionalRequest.get();
            logger.info("Return request");
        } else {
            logger.info("There is no such request in database");
        }
        return converter.convertToDTO(request);
    }

    @Transactional
    @Override
    public void delete(long id) {
        requestRepository.deleteById(id);
        logger.info("Request was deleted successful");
    }
}
