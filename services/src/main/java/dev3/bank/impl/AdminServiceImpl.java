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
        Collection<Client> clients = null;
        try {
            clients = clientDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Collection<Card> getAllUnlockCardRequest() {
        Collection<Card> cards = null;
        try {
            cards = unlockCardRequestDAO.getAll()
                    .stream()
                    .flatMap(unlockCardRequest -> {
                        Stream<Card> stream = null;
                        try {
                            stream = Stream.of(cardDAO.getByCardId(unlockCardRequest.getCardId()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return stream;
                    })
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Collection<Account> getAllUnlockAccountRequest() {
        Collection<Account> accounts = null;
        try {
            accounts = unlockAccountRequestDAO.getAll()
                    .stream()
                    .flatMap(accountRequest -> {
                        Stream<Account> stream = null;
                        try {
                            stream = Stream.of(accountDAO.getByAccountId(accountRequest.getAccountId()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return stream;
                    })
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void unlockCard(long cardId) {
        UnlockCardRequest request = null;
        Card card = null;
        try {
            request = unlockCardRequestDAO.getAll()
                    .stream()
                    .filter(unlockCardRequest -> unlockCardRequest.getCardId() == cardId)
                    .findFirst()
                    .orElse(null);
            card = cardDAO.getById(request.getCardId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Connection connection = DataBase.getConnection();
        try {
            connection.setAutoCommit(false);
            card.setLocked(false);
            cardDAO.update(card);
            unlockCardRequestDAO.delete(request.getId());
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                System.out.println("SQL exception");
            }
            System.out.println("SQL exception");
        }
    }

    @Override
    public void unlockAccount(long accountId) {
        UnlockAccountRequest request = null;
        Account account = null;
        try {
            request = unlockAccountRequestDAO.getAll()
                    .stream()
                    .filter(unlockAccountRequest -> unlockAccountRequest.getAccountId() == accountId)
                    .findFirst()
                    .orElse(null);
            account = accountDAO.getById(request.getAccountId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Connection connection = DataBase.getConnection();
        try {
            account.setLocked(false);
            connection.setAutoCommit(false);
            accountDAO.update(account);
            unlockAccountRequestDAO.delete(request.getId());
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                System.out.println("SQL exception");
            }
            System.out.println("SQL exception");
        }
    }


    @Override
    public Collection<Account> getAllAccounts() {
        Collection<Account> accounts = null;
        try {
            accounts = accountDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Collection<Card> getAllCards() {
        Collection<Card> cards = null;
        try {
            cards = cardDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Collection<News> getAllGeneralNews() {
        Collection<News> temp = null;
        try {
            temp = newsDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Collection<ClientNews> getAllClientNews() {
        Collection<ClientNews> temp = null;
        try {
            temp = clientNewsDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Collection<ClientNews> addClientNews(Collection<Long> clientIds, News news) {
        Collection<ClientNews> newsCollection = new ArrayList<>();
        for (Long clientId : clientIds) {
            ClientNews clientNews = new ClientNews();
            clientNews.setClientId(clientId);
            clientNews.setNewsId(news.getId());
            try {
                newsCollection.add(clientNewsDAO.add(clientNews));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return newsCollection;
    }

    @Override
    public News addGeneralNews(News news) {
        News temp = null;
        try {
            temp = newsDAO.add(news);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Collection<UnlockAccountRequest> getAllAccountRequest() {
        Collection<UnlockAccountRequest> temp = null;
        try {
            temp = unlockAccountRequestDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Collection<UnlockCardRequest> getAllCardRequest() {
        Collection<UnlockCardRequest> temp = null;
        try {
            temp = unlockCardRequestDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
