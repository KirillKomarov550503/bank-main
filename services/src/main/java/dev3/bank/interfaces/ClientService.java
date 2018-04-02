package dev3.bank.interfaces;

import dev3.bank.dto.TransactionDTO;
import dev3.bank.entity.*;
import dev3.bank.exception.TransactionException;

import java.util.Collection;

public interface ClientService {
    Account createAccount(Account account, long clientId);

    Card createCard(Card card, long accountId);

    Transaction createTransaction(TransactionDTO transactionDTO) throws TransactionException;

    Account lockAccount(long accountId);

    Card lockCard(long cardId);

    Collection<Account> getLockAccounts(long clientId);

    Collection<Card> getLockCards(long clientId);

    Collection<Card> getAllCardsByAccount(long accountId);

    Collection<Account> getUnlockAccounts(long clientId);

    Collection<Card> getUnlockCards(long clientId);

    Collection<Transaction> showStories(long clientId);

    UnlockCardRequest unlockCardRequest(long cardId);

    UnlockAccountRequest unlockAccountRequest(long accountId);

    Collection<News> getAllPersonalNews(long clientId);

    News getPersonalNews(long newsId);

    Account refill(long accountId);
}
