package dev3.bank.interfaces;

import dev3.bank.dto.TransactionDTO;
import dev3.bank.entity.Transaction;
import dev3.bank.exception.TransactionException;

import java.util.Collection;

public interface TransactionService {
    Transaction createTransaction(TransactionDTO transactionDTO) throws TransactionException;

    Collection<Transaction> showStories(long clientId);
}
