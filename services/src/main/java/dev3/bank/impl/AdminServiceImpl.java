package dev3.bank.impl;

import dev3.bank.dao.interfaces.*;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.*;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.AdminService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class AdminServiceImpl implements AdminService {
    private static AdminServiceImpl adminService;
    private AccountDAO accountDAO;
    private CardDAO cardDAO;
    private ClientDAO clientDAO;
    private NewsDAO newsDAO;
    private ClientNewsDAO clientNewsDAO;
    private UnlockCardRequestDAO unlockCardRequestDAO;
    private UnlockAccountRequestDAO unlockAccountRequestDAO;
    private AdminDAO adminDAO;
    private PersonDAO personDAO;

    private AdminServiceImpl() {
    }

    public static synchronized AdminServiceImpl getAdminService() {
        if (adminService == null) {
            adminService = new AdminServiceImpl();
        }
        return adminService;
    }

    @Override
    public void setDAO(DAOFactory daoFactory) {
        accountDAO = daoFactory.getAccountDAO();
        cardDAO = daoFactory.getCardDAO();
        clientDAO = daoFactory.getClientDAO();
        newsDAO = daoFactory.getNewsDAO();
        clientNewsDAO = daoFactory.getClientNewsDAO();
        unlockCardRequestDAO = daoFactory.getUnlockCardRequestDAO();
        unlockAccountRequestDAO = daoFactory.getUnlockAccountRequestDAO();
        adminDAO = daoFactory.getAdminDAO();
        personDAO = daoFactory.getPersonDAO();
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
        Collection<Card> cards = new ArrayList<>();
        try {
            Collection<UnlockCardRequest> requests = unlockCardRequestDAO.getAll();
            for (UnlockCardRequest request : requests) {
                cards.add(cardDAO.getById(request.getCardId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Collection<Account> getAllUnlockAccountRequest() {
        Collection<Account> accounts = new ArrayList<>();
        try {
            Collection<UnlockAccountRequest> requests = unlockAccountRequestDAO.getAll();
            for (UnlockAccountRequest request : requests) {
                accounts.add(accountDAO.getById(request.getAccountId()));
            }
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
    public Collection<ClientNews> addClientNews(Collection<Long> clientIds, long newsId) {
        Connection connection = DataBase.getConnection();
        Collection<ClientNews> newsCollection = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            for (Long clientId : clientIds) {
                ClientNews clientNews = new ClientNews();
                clientNews.setClientId(clientId);
                clientNews.setNewsId(newsId);
                clientNewsDAO.add(clientNews);
                newsCollection.add(clientNews);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("SQL exception");
            }
        }
        return newsCollection;
    }

    @Override
    public News addGeneralNews(News news, long adminId) {
        News temp = null;
        try {
            news.setAdminId(adminId);
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

    @Override
    public Admin addAdmin(Person person) {
        Connection connection = DataBase.getConnection();
        Admin adminRes = null;
        try {
            connection.setAutoCommit(false);
            Admin admin = new Admin();
            admin.setPersonId(personDAO.add(person).getId());
            adminRes = adminDAO.add(admin);
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
        return adminRes;
    }

    @Override
    public Collection<Admin> getAllAdmin() {
        Collection<Admin> admins = null;
        try {
            admins = adminDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    @Override
    public Collection<News> getAllNewsByStatus(NewsStatus newsStatus) {
        Collection<News> newsCollection = null;
        try {
            newsCollection = newsDAO.getNewsByStatus(newsStatus);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsCollection;
    }
}
