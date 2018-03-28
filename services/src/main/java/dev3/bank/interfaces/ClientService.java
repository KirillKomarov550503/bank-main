package dev3.bank.interfaces;

import dev3.bank.entity.Account;
import dev3.bank.entity.Card;
import dev3.bank.entity.Transaction;

import java.util.Collection;

public interface ClientService {
    Account createAccount(Account account, long clientId);

    Collection<Account> getAllAccounts(long clientId);

    Collection<Card> getAllCards(long clientId);

    Collection<Card> getAllCardsByAccount(long accountId, long clientId);

    Card createCard(Card card, long accountId, long clientId);

    Collection<Transaction> showStories(long clientId);

    void lockCard(long cardId, long clientId);

    void lockAccount(long accountId, long clientId);

    void unlockCardRequest(long cardId, long clientId);

    void unlockAccountRequest(long accountId, long clientId);

    Transaction createTransaction(Transaction transaction);
}
