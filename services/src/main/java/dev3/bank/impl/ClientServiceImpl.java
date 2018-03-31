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
        return accountDAO.getLockedAccounts();
    }

    @Override
    public Collection<Card> getLockCards(long clientId) {
        CardDAO cardDAO = new CardDAOImpl();
        return cardDAO.getLockedCards();
    }

    @Override
    public Collection<Account> getUnlockAccounts(long clientId) {
        AccountDAO accountDAO = new AccountDAOImpl();
        return accountDAO.getUnlockedAccounts();
    }

    @Override
    public Collection<Card> getUnlockCards(long clientId) {
        CardDAO cardDAO = new CardDAOImpl();
        return cardDAO.getUnlockedCards();
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
    public Transaction createTransaction(TransactionDTO transactionDTO) throws TransactionException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Transaction transaction = new Transaction();
        transaction.setDate(simpleDateFormat.format(new Date()));

        AccountDAO accountDAO = new AccountDAOImpl();
        Account accountFrom = accountDAO.getById(transactionDTO.getAccountFromId());
        if (accountFrom.getClient().getId() == transactionDTO.getClientId()) {
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

            transaction.setAccountFrom(accountFrom);
            transaction.setAccountTo(accountTo);
            TransactionDAO transactionDAO = new TransactionDAOImpl();
            return transactionDAO.add(transaction);
        }
        return null;
    }
}
