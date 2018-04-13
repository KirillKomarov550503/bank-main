package dev3.bank.interfaces;

import dev3.bank.entity.*;

import java.util.Collection;

public interface AdminService {

    Collection<Client> getAllClients();

    Collection<Account> getAllAccounts();

    Collection<Card> getAllCards();

    Collection<News> getAllGeneralNews();

    Collection<ClientNews> getAllClientNews();

    Collection<News> getAllNewsByStatus(NewsStatus newsStatus);

    void unlockCard(long cardId);

    Collection<Card> getAllUnlockCardRequest();

    void unlockAccount(long accountId);

    Collection<Account> getAllUnlockAccountRequest();

    News addGeneralNews(News news, long adminId);

    Collection<ClientNews> addClientNews(Collection<Long> clientIds, long newsId);

    Collection<UnlockAccountRequest> getAllAccountRequest();

    Collection<UnlockCardRequest> getAllCardRequest();

    Admin addAdmin(Person person);

    Collection<Admin> getAllAdmin();
}
