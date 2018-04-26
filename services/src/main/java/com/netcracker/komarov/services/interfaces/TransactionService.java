package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.Transaction;
import com.netcracker.komarov.services.dto.TransactionDTO;
import com.netcracker.komarov.services.exception.TransactionException;

import java.util.Collection;

public interface TransactionService {
    Transaction createTransaction(TransactionDTO transactionDTO) throws TransactionException;

    Collection<Transaction> showStories(long clientId);
}
