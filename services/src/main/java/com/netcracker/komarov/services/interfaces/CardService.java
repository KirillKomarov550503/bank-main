package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.Card;

import java.util.Collection;

public interface CardService {
    Card createCard(Card card, long accountId);

    Card lockCard(long cardId);

    Collection<Card> getLockCards(long clientId);

    Collection<Card> getAllCardsByAccount(long accountId);

    Collection<Card> getUnlockCards(long clientId);

    Collection<Card> getAllCards();

    Collection<Card> getAllUnlockCardRequest();

    Card unlockCard(long cardId);
}
