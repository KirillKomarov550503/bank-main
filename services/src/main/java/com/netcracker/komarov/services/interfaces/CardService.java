package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.CardDTO;

import java.util.Collection;

public interface CardService {
    CardDTO createCard(CardDTO card, long accountId);

    CardDTO lockCard(long cardId);

    Collection<CardDTO> getCardsByClientIdAndLock(long clientId, boolean lock);

    Collection<CardDTO> getAllCardsByAccountId(long accountId);

    Collection<CardDTO> getAllCards();

    Collection<CardDTO> getAllCardRequest();

    CardDTO unlockCard(long cardId);
}
