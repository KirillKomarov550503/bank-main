package dev3.bank.impl;

import dev3.bank.dao.impl.*;
import dev3.bank.dao.interfaces.*;
import dev3.bank.entity.*;
import dev3.bank.interfaces.AdminService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminServiceImpl implements AdminService {

    @Override
    public Collection<Client> getAllClients() {
        ClientDAO clientDAO = new ClientDAOImpl();
        return clientDAO.getAll();
    }

    @Override
    public Collection<Card> getAllUnlockCardRequest() {
        UnlockCardRequestDAO requestDAO = new UnlockCardRequestDAOImpl();
        return requestDAO.getAll()
                .stream()
                .flatMap(unlockCardRequest -> Stream.of(unlockCardRequest.getCard()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Account> getAllUnlockAccountRequest() {
        UnlockAccountRequestDAO requestDAO = new UnlockAccountRequestDAOImpl();
        return requestDAO.getAll()
                .stream()
                .flatMap(accountRequest -> Stream.of(accountRequest.getAccount()))
                .collect(Collectors.toList());
    }

    @Override
    public void unlockCard(long cardId) {
        UnlockCardRequestDAO unlockCardRequestDAO = new UnlockCardRequestDAOImpl();
        Collection<UnlockCardRequest> requests = unlockCardRequestDAO.getAll();
        long unlockCardRequstId = 0;
        for (UnlockCardRequest request : requests) {
            if (request.getCard().getId() == cardId) {
                request.getCard().setLocked(false);
                unlockCardRequstId = request.getId();
                break;
            }
        }
        unlockCardRequestDAO.delete(unlockCardRequstId);
    }

    @Override
    public void unlockAccount(long accountId) {
        UnlockAccountRequestDAO unlockAccountRequestDAO = new UnlockAccountRequestDAOImpl();
        Collection<UnlockAccountRequest> requests = unlockAccountRequestDAO.getAll();
        long unlockAccountRequestId = 0;
        for (UnlockAccountRequest request : requests) {
            if (request.getAccount().getId() == accountId) {
                request.getAccount().setLocked(false);
                unlockAccountRequestId = request.getId();
                break;
            }
        }
        unlockAccountRequestDAO.delete(unlockAccountRequestId);
    }


    @Override
    public Collection<Account> getAllAccounts() {
        AccountDAO accountDAO = new AccountDAOImpl();
        return accountDAO.getAll();
    }

    @Override
    public Collection<Card> getAllCards() {
        CardDAO cardDAO = new CardDAOImpl();
        return cardDAO.getAll();
    }

    @Override
    public Collection<News> getAllGeneralNews() {
        NewsDAO newsDAO = new NewsDAOImpl();
        return newsDAO.getAll();
    }

    @Override
    public Collection<ClientNews> getAllClientNews() {
        ClientNewsDAO clientNewsDAO = new ClientNewsDAOImpl();
        return clientNewsDAO.getAll();
    }

    @Override
    public Collection<ClientNews> addClientNews(Collection<Long> clientIds, News news) {
        Collection<ClientNews> newsCollection = new ArrayList<>();
        ClientNewsDAO clientNewsDAO = new ClientNewsDAOImpl();
        ClientDAO clientDAO = new ClientDAOImpl();
        for (Long clientId : clientIds) {
            ClientNews clientNews = new ClientNews();
            clientNews.setClient(clientDAO.getById(clientId));
            clientNews.setNews(news);
            newsCollection.add(clientNewsDAO.add(clientNews));
        }
        return newsCollection;
    }

    @Override
    public News addGeneralNews(News news) {
        NewsDAO newsDAO = new NewsDAOImpl();
        return newsDAO.add(news);
    }
}
