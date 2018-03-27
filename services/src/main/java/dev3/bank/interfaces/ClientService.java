package dev3.bank.interfaces;

import dev3.bank.entity.Account;
import dev3.bank.entity.Card;
import dev3.bank.entity.Transaction;

import java.util.Collection;

public interface ClientService {
    Account createAccount(Account account, long personId);

    Card createCard(Card card, long accountId);

    Collection<Transaction> showStories();

    void lockCard(long cardId);

    void lockAccount(long accountId);

    void unlockCardRequest(long cardId);

    void unlockAccountRequest(long accountId);

    Transaction crateTransaction(long accountFromId, long accountToId);
}
