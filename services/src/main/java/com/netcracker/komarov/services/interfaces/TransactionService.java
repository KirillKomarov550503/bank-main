package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.TransactionDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;

import java.util.Collection;

public interface TransactionService {
    TransactionDTO save(TransactionDTO transactionDTO, long clientId) throws LogicException,
            NotFoundException;

    Collection<TransactionDTO> findTransactionsByClientId(long clientId) throws NotFoundException;

    TransactionDTO findById(long transactionId) throws NotFoundException;

    boolean isContain(long clientId, long transactionId) throws NotFoundException, LogicException;
}
