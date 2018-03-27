package dev3.bank.impl;

import dev3.bank.dao.impl.AccountDAOImpl;
import dev3.bank.dao.impl.CardDAOImpl;
import dev3.bank.dao.impl.ClientDAOImpl;
import dev3.bank.dao.interfaces.AccountDAO;
import dev3.bank.dao.interfaces.CardDAO;
import dev3.bank.dao.interfaces.ClientDAO;
import dev3.bank.entity.Account;
import dev3.bank.entity.Card;
import dev3.bank.entity.Transaction;
import dev3.bank.interfaces.ClientService;

import java.util.Collection;

public class ClientServiceImpl implements ClientService {

    @Override
    public Account createAccount(Account account, long personId) {
        AccountDAO accountDAO = new AccountDAOImpl();
        Account acc = accountDAO.add(account);
        ClientDAO clientDAO = new ClientDAOImpl();
        clientDAO.getById(personId).getAccountCollection().add(acc);
        return acc;
    }

    @Override
    public Card createCard(Card card, long accountId) {
        CardDAO cardDAO = new CardDAOImpl();
        Card tempCard = cardDAO.add(card);
        AccountDAO accountDAO = new AccountDAOImpl();
        accountDAO.getById(accountId).getCardCollection().add(tempCard);
        return tempCard;
    }

    @Override
    public Collection<Transaction> showStories() {
        return null;
    }

    @Override
    public void lockCard(long cardId) {

    }

    @Override
    public void lockAccount(long accountId) {

    }

    @Override
    public void unlockCardRequest(long cardId) {

    }

    @Override
    public void unlockAccountRequest(long accountId) {

    }

    @Override
    public Transaction crateTransaction(long accountFromId, long accountToId) {
        return null;
    }
}
