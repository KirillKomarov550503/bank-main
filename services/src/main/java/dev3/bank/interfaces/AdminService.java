package dev3.bank.interfaces;

import dev3.bank.entity.Account;
import dev3.bank.entity.Card;
import dev3.bank.entity.Client;
import dev3.bank.entity.News;

import java.util.Collection;

public interface AdminService {

    Collection<Client> getAllClients();

    void unlockCard(long cardId);

    Collection<Card> getAllUnlockCardRequest();

    void unlockAccount(long accountId);

    Collection<Account> getAllUnlockAccountRequest();

    void sendNews(News news);
}
