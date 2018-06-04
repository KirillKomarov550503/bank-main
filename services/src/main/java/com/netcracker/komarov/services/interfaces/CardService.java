package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;

import java.util.Collection;

public interface CardService {
    CardDTO save(CardDTO card) throws NotFoundException, LogicException;

    CardDTO lockCard(long cardId) throws LogicException, NotFoundException;

    Collection<CardDTO> findCardsByClientIdAndLock(long personId, boolean lock) throws NotFoundException;

    Collection<CardDTO> findCardsByAccountId(long accountId) throws NotFoundException;

    Collection<CardDTO> findAllCards();

    CardDTO unlockCard(long cardId) throws LogicException, NotFoundException;

    void deleteById(long cardId) throws NotFoundException;

    CardDTO findById(long cardId) throws NotFoundException;

    boolean isContain(long accountId, long cardId) throws NotFoundException;
}
