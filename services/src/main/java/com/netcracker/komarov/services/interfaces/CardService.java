package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;

import java.util.Collection;

public interface CardService {
    CardDTO createCard(CardDTO card);

    CardDTO lockCard(long cardId) throws LogicException, NotFoundException;

    Collection<CardDTO> getCardsByClientIdAndLock(long clientId, boolean lock) throws NotFoundException;

    Collection<CardDTO> getAllCardsByAccountId(long accountId) throws NotFoundException;

    Collection<CardDTO> getAllCards();

    CardDTO unlockCard(long cardId) throws LogicException, NotFoundException;

    void deleteById(long cardId) throws NotFoundException;

    CardDTO findById(long cardId) throws NotFoundException;
}
