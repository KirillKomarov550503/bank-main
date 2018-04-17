package dev3.bank.interfaces;

import dev3.bank.entity.Card;

import java.util.Collection;

public interface CardService extends BaseService {
    Card createCard(Card card, long accountId);

    Card lockCard(long cardId);

    Collection<Card> getLockCards(long clientId);

    Collection<Card> getAllCardsByAccount(long accountId);

    Collection<Card> getUnlockCards(long clientId);

    Collection<Card> getAllCards();

    Collection<Card> getAllUnlockCardRequest();

    void unlockCard(long cardId);
}
