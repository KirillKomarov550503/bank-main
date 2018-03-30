package dev3.bank.impl;

import dev3.bank.dao.impl.*;
import dev3.bank.dao.interfaces.*;
import dev3.bank.entity.*;
import dev3.bank.interfaces.ClientService;

import java.util.Collection;

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
        UnlockCardRequestDAO unlockCardRequestDAO = new UnlockCardRequestDAOImpl();
        CardDAO cardDAO = new CardDAOImpl();
        UnlockCardRequest unlockCardRequest = new UnlockCardRequest();
        unlockCardRequest.setCard(cardDAO.getById(cardId));
        return unlockCardRequestDAO.add(unlockCardRequest);
    }

    @Override
    public UnlockAccountRequest unlockAccountRequest(long accountId) {
        UnlockAccountRequestDAO unlockAccountRequestDAO = new UnlockAccountRequestDAOImpl();
        AccountDAO accountDAO = new AccountDAOImpl();
        UnlockAccountRequest request = new UnlockAccountRequest();
        request.setAccount(accountDAO.getById(accountId));
        return unlockAccountRequestDAO.add(request);
    }

    @Override
    public Transaction createTransaction(long accountFromId, long accountToId) {
        return null;
    }
}
