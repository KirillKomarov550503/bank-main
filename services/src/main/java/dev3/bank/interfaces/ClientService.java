package dev3.bank.interfaces;

import dev3.bank.dto.TransactionDTO;
import dev3.bank.entity.*;
import dev3.bank.exception.NotEnoughMoneyException;

import java.util.Collection;

public interface ClientService {
    Account createAccount(Account account, long clientId);

    Collection<Account> getAllAccounts(long clientId);

    Collection<Card> getAllCards(long clientId);

    Collection<Card> getAllCardsByAccount(long accountId);

    Card createCard(Card card, long accountId);

    Collection<Transaction> showStories(long clientId);

    void lockCard(long cardId);

    void lockAccount(long accountId);

    UnlockCardRequest unlockCardRequest(long cardId);

    UnlockAccountRequest unlockAccountRequest(long accountId);

    Transaction createTransaction(TransactionDTO transactionDTO) throws NotEnoughMoneyException;
}
