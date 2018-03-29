package dev3.bank.impl;

import dev3.bank.dao.impl.AccountDAOImpl;
import dev3.bank.dao.impl.CardDAOImpl;
import dev3.bank.dao.impl.ClientDAOImpl;
import dev3.bank.dao.impl.TransactionDAOImpl;
import dev3.bank.dao.interfaces.AccountDAO;
import dev3.bank.dao.interfaces.CardDAO;
import dev3.bank.dao.interfaces.ClientDAO;
import dev3.bank.dao.interfaces.TransactionDAO;
import dev3.bank.entity.Account;
import dev3.bank.entity.Card;
import dev3.bank.entity.Client;
import dev3.bank.entity.Transaction;
import dev3.bank.interfaces.ClientService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {

    @Override
    public Account createAccount(Account account, long clientId) {
        AccountDAO accountDAO = new AccountDAOImpl();
        Account acc = accountDAO.add(account);
        ClientDAO clientDAO = new ClientDAOImpl();
        clientDAO.getById(clientId).getAccountCollection().add(acc);
        return acc;
    }

    @Override
    public Collection<Account> getAllAccounts(long clientId) {
        ClientDAO clientDAO = new ClientDAOImpl();
        return clientDAO.getById(clientId).getAccountCollection();
    }

    @Override
    public Collection<Card> getAllCards(long clientId) {
        ClientDAO clientDAO = new ClientDAOImpl();
        return clientDAO.getById(clientId).getAccountCollection()
                .stream()
                .flatMap(acc -> acc.getCardCollection().stream())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Card> getAllCardsByAccount(long accountId, long clientId) {
        ClientDAO clientDAO = new ClientDAOImpl();
        return clientDAO.getById(clientId).getAccountCollection()
                .stream()
                .filter(account -> account.getId() == accountId)
                .collect(Collectors.toList())
                .get(0)
                .getCardCollection();
    }

    @Override
    public Card createCard(Card card, long accountId, long clientId) {
        CardDAO cardDAO = new CardDAOImpl();
        Card tempCard = cardDAO.add(card);
        ClientDAO clientDAO = new ClientDAOImpl();
        ArrayList<Client> clients = (ArrayList<Client>) clientDAO.getAll();
        for (int i = 0; i < clients.size(); i++) {
            ArrayList<Account> accounts = (ArrayList<Account>) clients.get(i).getAccountCollection();
            for (int j = 0; j < accounts.size(); j++) {
                if (accounts.get(i).getId() == accountId) {
                    accounts.get(i).getCardCollection().add(tempCard);
                }
            }
        }
        AccountDAO accountDAO = new AccountDAOImpl();
        accountDAO.getById(accountId).getCardCollection().add(tempCard);
        return tempCard;
    }

    @Override
    public Collection<Transaction> showStories(long clientId) {
        ClientDAO clientDAO = new ClientDAOImpl();
        Collection<Account> accounts = clientDAO.getByAccountId(clientId).getAccountCollection();
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        Collection<Transaction> transactions = new ArrayList<>();
        for (Account account : accounts) {
            transactions.addAll(transactionDAO.getByAccountFromId(account.getId()));
        }
        return transactions;
    }

    @Override
    public void lockCard(long cardId, long clientId) {

    }

    @Override
    public void lockAccount(long accountId, long clientId) {

    }

    @Override
    public void unlockCardRequest(long cardId, long clientId) {

    }

    @Override
    public void unlockAccountRequest(long accountId, long clientId) {

    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        double money = transaction.getMoney();
        AccountDAO accountDAO = new AccountDAOImpl();

        long accountFromId = transaction.getAccountFrom().getId();
        Account accountFrom = accountDAO.getById(accountFromId);
        double accountFromBalance = accountFrom.getBalance();
        accountFrom.setBalance(accountFromBalance - money);

        long accountToId = transaction.getAccountTo().getId();
        Account accountTo = accountDAO.getById(accountToId);
        double accountToBalance = accountTo.getBalance();
        accountTo.setBalance(accountToBalance + money);
        Transaction temp = transactionDAO.add(transaction);

        ClientDAO clientDAO = new ClientDAOImpl();
        Client clientFrom = clientDAO.getByAccountId(accountFromId);
        ArrayList<Account> accountsFrom = (ArrayList<Account>) clientFrom.getAccountCollection();
        for (Account account : accountsFrom) {
            if (account.getId() == accountFromId) {
                account.setBalance(accountFromBalance - money);
            }
        }

        Client clientTo = clientDAO.getByAccountId(accountToId);
        ArrayList<Account> accountsTo = (ArrayList<Account>) clientTo.getAccountCollection();
        for (Account account : accountsTo) {
            if (account.getId() == accountFromId) {
                account.setBalance(accountFromBalance + money);
            }
        }

        return temp;
    }
}
