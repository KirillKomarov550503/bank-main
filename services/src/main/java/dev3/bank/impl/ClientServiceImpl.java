package dev3.bank.impl;

import dev3.bank.dao.impl.*;
import dev3.bank.dao.interfaces.*;
import dev3.bank.entity.*;
import dev3.bank.exception.NotEnoughMoneyException;
import dev3.bank.interfaces.ClientService;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

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
    public Collection<Account> getAllAccounts(long clientId) {
        AccountDAO accountDAO = new AccountDAOImpl();
        return accountDAO.getAccountsByClientId(clientId);
    }

    @Override
    public Collection<Card> getAllCards(long clientId) {
        CardDAO cardDAO = new CardDAOImpl();
        return cardDAO.getCardsByClientId(clientId);
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
    public void lockCard(long cardId) {
        CardDAO cardDAO = new CardDAOImpl();
        Card card = cardDAO.getById(cardId);
        card.setLocked(true);
    }

    @Override
    public void lockAccount(long accountId) {
        AccountDAO accountDAO = new AccountDAOImpl();
        Account account = accountDAO.getById(accountId);
        account.setLocked(true);
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
    public Transaction createTransaction(long accountFromId, long accountToId, double money, long clientId) throws NotEnoughMoneyException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Transaction transaction = new Transaction();
        transaction.setDate(simpleDateFormat.format(new Date()));

        AccountDAO accountDAO = new AccountDAOImpl();
        Account accountFrom = accountDAO.getById(accountFromId);
        if (accountFrom.getClient().getId() == clientId) {
            Account accountTo = accountDAO.getById(accountToId);

            double moneyFrom = accountFrom.getBalance();
            if(money > moneyFrom)
                throw new NotEnoughMoneyException("Not enough money on your account");
            double moneyTo = accountTo.getBalance();
            accountFrom.setBalance(moneyFrom - money);
            accountTo.setBalance(moneyTo + money);

            transaction.setAccountFrom(accountFrom);
            transaction.setAccountTo(accountTo);
            return transaction;
        }
        return null;
    }
}
