package com.netcracker.komarov.console.utils;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Card;

public class Producer {
    public static Account produceAccount() {
        Account account = new Account();
        account.setBalance(0.0);
        account.setLocked(false);
        return account;
    }

    public static Card produceCard() {
        Card card = new Card();
        card.setLocked(false);
        return card;
    }
}
