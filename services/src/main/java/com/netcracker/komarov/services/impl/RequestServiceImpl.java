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
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
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
    public RequestDTO saveRequest(long requestId, RequestStatus requestStatus) throws LogicException, NotFoundException {
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
                String error = "This account have already added to requests";
                logger.error(error);
                throw new LogicException(error);
            } else {
                Optional<Account> optionalAccount = accountRepository.findById(requestId);
                if (optionalAccount.isPresent()) {
                    Account account = optionalAccount.get();
                    if(account.isLocked()){
                        account.setRequest(request);
                        request.setAccount(account);
                        res = requestRepository.save(request);
                        logger.info("Add request to unlock account");
                    } else {
                        String error= "This account is unlocking";
                        logger.error(error);
                        throw new LogicException(error);
                    }
                } else {
                    String error = "There is no such account in database";
                    logger.error(error);
                    throw new NotFoundException(error);
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
                    String error = "This card have already added to requests";
                    logger.error(error);
                    throw new LogicException(error);
                } else {
                    Optional<Card> optionalCard = cardRepository.findById(requestId);
                    if (optionalCard.isPresent()) {
                        Card card = optionalCard.get();
                        if(card.isLocked()){
                            card.setRequest(request);
                            request.setCard(card);
                            res = requestRepository.save(request);
                            logger.info("Add request to unlock card");
                        } else {
                            String error= "This card is unlocking";
                            logger.error(error);
                            throw new LogicException(error);
                        }
                    } else {
                        String error = "There is no such card in database";
                        logger.error(error);
                        throw new NotFoundException(error);
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
    public RequestDTO findById(long id) throws NotFoundException {
        Request request;
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            request = optionalRequest.get();
            logger.info("Return request");
        } else {
            String error = "There is no such request in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
        return converter.convertToDTO(request);
    }

    @Transactional
    @Override
    public void delete(long id) throws NotFoundException {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            requestRepository.deleteRequestById(id);
            logger.info("Request was deleted successful");
        } else {
            String error = "There is no such request in database";
            logger.error(error);
            throw new NotFoundException(error);
        }
    }
}
