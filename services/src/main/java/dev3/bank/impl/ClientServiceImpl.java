package dev3.bank.impl;

import dev3.bank.dao.interfaces.*;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.dto.TransactionDTO;
import dev3.bank.entity.*;
import dev3.bank.exception.TransactionException;
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

    public void setTransactionDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    private ClientServiceImpl() {
    }

    public static synchronized ClientServiceImpl getClientService() {
        if (clientService == null) {
            clientService = new ClientServiceImpl();
        }
        return clientService;

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
    public Account createAccount(Account account, long clientId) {
        account.setClientId(clientId);
        return accountDAO.add(account);
    }

    @Override
    public Card createCard(Card card, long accountId) {
        card.setAccountId(accountId);
        return cardDAO.add(card);
    }


    @Override
    public Collection<Account> getLockAccounts(long clientId) {
        return accountDAO.getLockedAccountsByClientId(clientId);
    }

    @Override
    public Collection<Card> getLockCards(long clientId) {
        return cardDAO.getLockedCardsByClientId(clientId);
    }

    @Override
    public Collection<Account> getUnlockAccounts(long clientId) {
        return accountDAO.getUnlockedAccountsByClientId(clientId);
    }


    @Override
    public Collection<Card> getUnlockCards(long clientId) {
        return cardDAO.getUnlockedCardsByClientId(clientId);
    }

    @Override
    public Collection<Card> getAllCardsByAccount(long accountId) {
        return cardDAO.getCardsByAccountId(accountId);
    }


    @Override
    public Collection<Transaction> showStories(long clientId) {
        return transactionDAO.getByClientId(clientId);
    }

    @Override
    public Card lockCard(long cardId) {
        Card card = cardDAO.getById(cardId);
        card.setLocked(true);
        return cardDAO.update(card);
    }

    @Override
    public Account lockAccount(long accountId) {
        Account account = accountDAO.getById(accountId);
        account.setLocked(true);
        return accountDAO.update(account);
    }

    @Override
    public UnlockCardRequest unlockCardRequest(long cardId) {
        if (unlockCardRequestDAO.getByCardId(cardId) == null) {
            UnlockCardRequest request = new UnlockCardRequest();
            request.setCardId(cardId);
            return unlockCardRequestDAO.add(request);
        } else {
            return null;
        }
    }

    @Override
    public UnlockAccountRequest unlockAccountRequest(long accountId) {
        if (unlockAccountRequestDAO.getByAccountId(accountId) == null) {
            UnlockAccountRequest request = new UnlockAccountRequest();
            request.setAccountId(accountId);
            return unlockAccountRequestDAO.add(request);
        } else {
            return null;
        }
    }

    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) throws TransactionException {
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
            Connection connection = DataBase.getConnection();
            Transaction transaction = new Transaction();
            Transaction newTransaction = null;
            try {
                connection.setAutoCommit(false);
                connection.commit();
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
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    System.out.println("SQL exception");
                }
                System.out.println("SQL exception");
            }
            return newTransaction;
        }
        return null;
    }

    @Override
    public Account refill(long accountId) {
        Account account = accountDAO.getById(accountId);
        double balance = account.getBalance();
        account.setBalance(balance + 100.0);
        return accountDAO.update(account);
    }

    @Override
    public Collection<News> getAllPersonalNews(long clientId) {
        return clientNewsDAO.getAllByClientId(clientId)
                .stream()
                .flatMap(clientNews -> Stream.of(newsDAO.getById(clientNews.getNewsId())))
                .collect(Collectors.toList());
    }

    @Override
    public News getPersonalNews(long newsId) {
        return newsDAO.getById(newsId);
    }
}
