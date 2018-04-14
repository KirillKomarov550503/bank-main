package dev3.bank.impl;

import dev3.bank.dao.interfaces.*;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.dto.TransactionDTO;
import dev3.bank.entity.*;
import dev3.bank.exception.TransactionException;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.ClientService;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientServiceImpl implements ClientService {
    private static ClientServiceImpl clientService;
    private AccountDAO accountDAO;
    private CardDAO cardDAO;
    private ClientDAO clientDAO;
    private NewsDAO newsDAO;
    private ClientNewsDAO clientNewsDAO;
    private UnlockCardRequestDAO unlockCardRequestDAO;
    private UnlockAccountRequestDAO unlockAccountRequestDAO;
    private TransactionDAO transactionDAO;

    private ClientServiceImpl() {
    }

    public static synchronized ClientServiceImpl getClientService() {
        if (clientService == null) {
            clientService = new ClientServiceImpl();
        }
        return clientService;

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
        transactionDAO = daoFactory.getTransactionDAO();
    }

    @Override
    public Account createAccount(Account account, long clientId) {
        account.setClientId(clientId);
        Account temp = null;
        try {
            temp = accountDAO.add(account);
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }
        return temp;
    }

    @Override
    public Card createCard(Card card, long accountId) {
        card.setAccountId(accountId);
        Card temp = null;
        try {
            temp = cardDAO.add(card);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }


    @Override
    public Collection<Account> getLockAccounts(long clientId) {
        Collection<Account> accounts = null;
        try {
            accounts = accountDAO.getLockedAccountsByClientId(clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Collection<Card> getLockCards(long clientId) {
        Collection<Card> cards = null;
        try {
            cards = cardDAO.getLockedCardsByClientId(clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Collection<Account> getUnlockAccounts(long clientId) {
        Collection<Account> accounts = null;
        try {
            accounts = accountDAO.getUnlockedAccountsByClientId(clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }


    @Override
    public Collection<Card> getUnlockCards(long clientId) {
        Collection<Card> cards = null;
        try {
            cards = cardDAO.getUnlockedCardsByClientId(clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Collection<Card> getAllCardsByAccount(long accountId) {
        Collection<Card> cards = null;
        try {
            cards = cardDAO.getCardsByAccountId(accountId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }


    @Override
    public Collection<Transaction> showStories(long clientId) {
        Collection<Transaction> transactions = null;
        try {
            transactions = transactionDAO.getByClientId(clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public Card lockCard(long cardId) {
        Card temp = null;
        try {
            Card card = cardDAO.getById(cardId);
            card.setLocked(true);
            temp = cardDAO.update(card);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Account lockAccount(long accountId) {
        Account temp = null;
        try {
            Account account = accountDAO.getById(accountId);
            account.setLocked(true);
            temp = accountDAO.update(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public UnlockCardRequest unlockCardRequest(long cardId) {
        UnlockCardRequest temp = null;
        try {
            if (unlockCardRequestDAO.getByCardId(cardId) == null) {
                UnlockCardRequest request = new UnlockCardRequest();
                request.setCardId(cardId);
                temp = unlockCardRequestDAO.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public UnlockAccountRequest unlockAccountRequest(long accountId) {
        UnlockAccountRequest temp = null;
        try {
            if (unlockAccountRequestDAO.getByAccountId(accountId) == null) {
                UnlockAccountRequest request = new UnlockAccountRequest();
                request.setAccountId(accountId);
                temp = unlockAccountRequestDAO.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) throws TransactionException {
        Connection connection = DataBase.getConnection();
        Transaction newTransaction = null;
        try {
            Account accountFrom = accountDAO.getById(transactionDTO.getAccountFromId());
            if (accountFrom.getClientId() == transactionDTO.getClientId()) {
                Account accountTo = accountDAO.getById(transactionDTO.getAccountToId());
                if (accountFrom.isLocked()) {
                    throw new TransactionException("Your account is lock");
                }
                if (accountTo.isLocked()) {
                    throw new TransactionException("Other account is lock");
                }
                double moneyFrom = accountFrom.getBalance();
                double transactionMoney = transactionDTO.getMoney();
                if (moneyFrom < transactionMoney) {
                    throw new TransactionException("Not enough money on your account");
                }
                double moneyTo = accountTo.getBalance();
                accountFrom.setBalance(moneyFrom - transactionMoney);
                accountTo.setBalance(moneyTo + transactionMoney);
                Transaction transaction = new Transaction();

                connection.setAutoCommit(false);
                accountDAO.update(accountFrom);
                accountDAO.update(accountTo);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                transaction.setDate(simpleDateFormat.format(new Date()));
                transaction.setAccountFromId(accountFrom.getId());
                transaction.setAccountToId(accountTo.getId());
                transaction.setMoney(transactionMoney);
                newTransaction = transactionDAO.add(transaction);
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return newTransaction;
    }

    @Override
    public Account refill(long accountId) {
        Account temp = null;
        try {
            Account account = accountDAO.getById(accountId);
            double balance = account.getBalance();
            account.setBalance(balance + 100.0);
            temp = accountDAO.update(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Collection<News> getAllPersonalNews(long clientId) {
        Collection<News> newsCollection = null;
        try {
            newsCollection = clientNewsDAO.getAllByClientId(clientId)
                    .stream()
                    .flatMap(clientNews -> {
                        Stream<News> stream = null;
                        try {
                            stream = Stream.of(newsDAO.getById(clientNews.getNewsId()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return stream;
                    })
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsCollection;
    }

    @Override
    public News getPersonalNews(long newsId) {
        News temp = null;
        try {
            temp = newsDAO.getById(newsId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
