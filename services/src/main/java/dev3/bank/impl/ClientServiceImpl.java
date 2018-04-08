package dev3.bank.impl;

import dev3.bank.dao.impl.*;
import dev3.bank.dao.interfaces.*;
import dev3.bank.dto.TransactionDTO;
import dev3.bank.entity.*;
import dev3.bank.exception.TransactionException;
import dev3.bank.interfaces.ClientService;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientServiceImpl implements ClientService {

    @Override
    public Account createAccount(Account account, long clientId) {
        ClientDAO clientDAO = new ClientDAOImpl();
        account.setClient(clientDAO.getById(clientId));
        AccountDAO accountDAO = new AccountDAOImpl();
        return accountDAO.add(account);
    }

    @Override
    public Card createCard(Card card, long accountId) {
        CardDAO cardDAO = new CardDAOImpl();
        AccountDAO accountDAO = new AccountDAOImpl();
        card.setAccount(accountDAO.getById(accountId));
        return cardDAO.add(card);
    }


    @Override
    public Collection<Account> getLockAccounts(long clientId) {
        AccountDAO accountDAO = new AccountDAOImpl();
        return accountDAO.getLockedAccountsByClientId(clientId);
    }

    @Override
    public Collection<Card> getLockCards(long clientId) {
        CardDAO cardDAO = new CardDAOImpl();
        return cardDAO.getLockedCardsByClientId(clientId);
    }

    @Override
    public Collection<Account> getUnlockAccounts(long clientId) {
        AccountDAO accountDAO = new AccountDAOImpl();
        return accountDAO.getUnlockedAccountsByClientId(clientId);
    }


    @Override
    public Collection<Card> getUnlockCards(long clientId) {
        CardDAO cardDAO = new CardDAOImpl();
        return cardDAO.getUnlockedCardsByClientId(clientId);
    }

    @Override
    public Collection<Card> getAllCardsByAccount(long accountId) {
        CardDAO cardDAO = new CardDAOImpl();
        return cardDAO.getCardsByAccountId(accountId);
    }


    @Override
    public Collection<Transaction> showStories(long clientId) {
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        return transactionDAO.getByClientId(clientId);
    }

    @Override
    public Card lockCard(long cardId) {
        CardDAO cardDAO = new CardDAOImpl();
        Card card = cardDAO.getById(cardId);
        card.setLocked(true);
        return cardDAO.update(card);
    }

    @Override
    public Account lockAccount(long accountId) {
        AccountDAO accountDAO = new AccountDAOImpl();
        Account account = accountDAO.getById(accountId);
        account.setLocked(true);
        return accountDAO.update(account);
    }

    @Override
    public UnlockCardRequest unlockCardRequest(long cardId) {
        UnlockCardRequestDAO requestDAO = new UnlockCardRequestDAOImpl();
        if (requestDAO.getByCardId(cardId) == null) {
            CardDAO cardDAO = new CardDAOImpl();
            UnlockCardRequest request = new UnlockCardRequest();
            request.setCard(cardDAO.getById(cardId));
            return requestDAO.add(request);
        } else {
            return null;
        }
    }

    @Override
    public UnlockAccountRequest unlockAccountRequest(long accountId) {
        UnlockAccountRequestDAO requestDAO = new UnlockAccountRequestDAOImpl();
        if (requestDAO.getByAccountId(accountId) == null) {
            AccountDAO accountDAO = new AccountDAOImpl();
            UnlockAccountRequest request = new UnlockAccountRequest();
            request.setAccount(accountDAO.getById(accountId));
            return requestDAO.add(request);
        } else {
            return null;
        }
    }

    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) throws TransactionException {
        AccountDAO accountDAO = new AccountDAOImpl();
        Account accountFrom = accountDAO.getById(transactionDTO.getAccountFromId());
        if (accountFrom.getClient().getId() == transactionDTO.getClientId()) {
            Account accountTo = accountDAO.getById(transactionDTO.getAccountToId());
            if (accountFrom.isLocked()) {
                throw new TransactionException("Your account is lock");
            }
            if (accountTo.isLocked()) {
                throw new TransactionException("Another account is lock");
            }
            double moneyFrom = accountFrom.getBalance();
            double transactionMoney = transactionDTO.getMoney();
            if (moneyFrom < transactionMoney) {
                throw new TransactionException("Not enough money");
            }
            double moneyTo = accountTo.getBalance();
            accountFrom.setBalance(moneyFrom - transactionMoney);
            accountTo.setBalance(moneyTo + transactionMoney);

            accountDAO.update(accountFrom);
            accountDAO.update(accountTo);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Transaction transaction = new Transaction();
            transaction.setDate(simpleDateFormat.format(new Date()));
            transaction.setAccountFrom(accountFrom);
            transaction.setAccountTo(accountTo);
            transaction.setMoney(transactionMoney);
            TransactionDAO transactionDAO = new TransactionDAOImpl();
            return transactionDAO.add(transaction);
        }
        return null;
    }

    @Override
    public Account refill(long accountId) {
        AccountDAO accountDAO = new AccountDAOImpl();
        Account account = accountDAO.getById(accountId);
        double balance = account.getBalance();
        account.setBalance(balance + 100.0);
        return accountDAO.update(account);
    }

    @Override
    public Collection<News> getAllPersonalNews(long clientId) {
        ClientNewsDAO clientNewsDAO = new ClientNewsDAOImpl();
        return clientNewsDAO.getAllByClientId(clientId)
                .stream()
                .flatMap(clientNews -> Stream.of(clientNews.getNews()))
                .collect(Collectors.toList());
    }

    @Override
    public News getPersonalNews(long newsId) {
        NewsDAO newsDAO = new NewsDAOImpl();
        return newsDAO.getById(newsId);
    }
}
