package dev3.bank.impl;

import dev3.bank.entity.Account;
import dev3.bank.entity.Card;
import dev3.bank.entity.Client;
import dev3.bank.entity.News;
import dev3.bank.interfaces.AdminService;

import java.util.Collection;

public class AdminServiceImpl implements AdminService{

    @Override
    public Collection<Client> getAllClients() {
        return null;
    }

    @Override
    public void unlockCard(long cardId) {

    }

    @Override
    public Collection<Card> getAllCards() {
        return null;
    }

    @Override
    public void unlockAccount() {

    }

    @Override
    public Collection<Account> getAllAccounts() {
        return null;
    }

    @Override
    public void sendNews(News news) {

    }
}
