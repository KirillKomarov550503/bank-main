package dev3.bank.impl;

import dev3.bank.dao.interfaces.*;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.*;
import dev3.bank.interfaces.AdminService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminServiceImpl implements AdminService {
    private static AdminServiceImpl adminService;
    private AccountDAO accountDAO;
    private CardDAO cardDAO;
    private ClientDAO clientDAO;
    private NewsDAO newsDAO;
    private ClientNewsDAO clientNewsDAO;
    private UnlockCardRequestDAO unlockCardRequestDAO;
    private UnlockAccountRequestDAO unlockAccountRequestDAO;

    private AdminServiceImpl() {
    }

    public static synchronized AdminServiceImpl getAdminService() {
        if (adminService == null) {
            adminService = new AdminServiceImpl();
        }
        return adminService;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void setCardDAO(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public void setNewsDAO(NewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }

    public void setClientNewsDAO(ClientNewsDAO clientNewsDAO) {
        this.clientNewsDAO = clientNewsDAO;
    }

    public void setUnlockCardRequestDAO(UnlockCardRequestDAO unlockCardRequestDAO) {
        this.unlockCardRequestDAO = unlockCardRequestDAO;
    }

    public void setUnlockAccountRequestDAO(UnlockAccountRequestDAO unlockAccountRequestDAO) {
        this.unlockAccountRequestDAO = unlockAccountRequestDAO;
    }

    @Override
    public Collection<Client> getAllClients() {
        return clientDAO.getAll();
    }

    @Override
    public Collection<Card> getAllUnlockCardRequest() {
        return unlockCardRequestDAO.getAll()
                .stream()
                .flatMap(unlockCardRequest -> Stream.of(cardDAO.getByCardId(unlockCardRequest.getCardId())))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Account> getAllUnlockAccountRequest() {
        return unlockAccountRequestDAO.getAll()
                .stream()
                .flatMap(accountRequest -> Stream.of(accountDAO.getByAccountId(accountRequest.getAccountId())))
                .collect(Collectors.toList());
    }

    @Override
    public void unlockCard(long cardId) {
        UnlockCardRequest request = unlockCardRequestDAO.getAll()
                .stream()
                .filter(unlockCardRequest -> unlockCardRequest.getCardId() == cardId)
                .findFirst()
                .orElse(null);
        Card card = cardDAO.getById(request.getCardId());
        Connection connection = DataBase.getConnection();
        try {
            connection.setAutoCommit(false);
            connection.commit();
            card.setLocked(false);
            cardDAO.update(card);
            unlockCardRequestDAO.delete(request.getId());
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println("SQL exception");
            }
            System.out.println("SQL exception");
        }
    }

    @Override
    public void unlockAccount(long accountId) {
        UnlockAccountRequest request = unlockAccountRequestDAO.getAll()
                .stream()
                .filter(unlockAccountRequest -> unlockAccountRequest.getAccountId() == accountId)
                .findFirst()
                .orElse(null);
        Account account = accountDAO.getById(request.getAccountId());
        Connection connection = DataBase.getConnection();
        try {
            account.setLocked(false);
            connection.setAutoCommit(false);
            connection.commit();
            accountDAO.update(account);
            unlockAccountRequestDAO.delete(request.getId());
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println("SQL exception");
            }
            System.out.println("SQL exception");
        }
    }


    @Override
    public Collection<Account> getAllAccounts() {
        return accountDAO.getAll();
    }

    @Override
    public Collection<Card> getAllCards() {
        return cardDAO.getAll();
    }

    @Override
    public Collection<News> getAllGeneralNews() {
        return newsDAO.getAll();
    }

    @Override
    public Collection<ClientNews> getAllClientNews() {
        return clientNewsDAO.getAll();
    }

    @Override
    public Collection<ClientNews> addClientNews(Collection<Long> clientIds, News news) {
        Collection<ClientNews> newsCollection = new ArrayList<>();
        for (Long clientId : clientIds) {
            ClientNews clientNews = new ClientNews();
            clientNews.setClientId(clientId);
            clientNews.setNewsId(news.getId());
            newsCollection.add(clientNewsDAO.add(clientNews));
        }
        return newsCollection;
    }

    @Override
    public News addGeneralNews(News news) {
        return newsDAO.add(news);
    }

    @Override
    public Collection<UnlockAccountRequest> getAllAccountRequest() {
        return unlockAccountRequestDAO.getAll();
    }

    @Override
    public Collection<UnlockCardRequest> getAllCardRequest() {
        return unlockCardRequestDAO.getAll();
    }
}
