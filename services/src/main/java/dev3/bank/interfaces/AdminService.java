package dev3.bank.interfaces;

import dev3.bank.entity.*;

import java.util.Collection;

public interface AdminService {

    Collection<Client> getAllClients();

    Collection<Account> getAllAccounts();

    Collection<Card> getAllCards();

    Collection<News> getAllGeneralNews();

    Collection<ClientNews> getAllClientNews();



    void unlockCard(long cardId);

    Collection<Card> getAllUnlockCardRequest();

    void unlockAccount(long accountId);

    Collection<Account> getAllUnlockAccountRequest();

    News addGeneralNews(News news);

    Collection<ClientNews> addClientNews(Collection<Long> clientIds, News news);
}
