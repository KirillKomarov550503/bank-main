package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.CardDTO;

import java.util.Collection;

public interface CardService {
    CardDTO createCard(CardDTO card, long accountId);

    CardDTO lockCard(long cardId);

    Collection<CardDTO> getLockCards(long clientId);

    Collection<CardDTO> getAllCardsByAccount(long accountId);

    Collection<CardDTO> getUnlockCards(long clientId);

    Collection<CardDTO> getAllCards();

    Collection<CardDTO> getAllUnlockCardRequest();

    void unlockCard(long cardId);
}
