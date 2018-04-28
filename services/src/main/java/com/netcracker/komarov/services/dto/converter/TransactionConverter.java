package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.Transaction;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.TransactionDTO;

public class TransactionConverter implements Converter<TransactionDTO, Transaction> {
    @Override
    public TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setMoney(transaction.getMoney());
        dto.setAccountFromId(transaction.getAccountFromId());
        dto.setAccountToId(transaction.getAccountToId());
        dto.setDate(transaction.getDate());
        return dto;
    }

    @Override
    public Transaction convertToEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setAccountFromId(transactionDTO.getAccountFromId());
        transaction.setAccountToId(transactionDTO.getAccountToId());
        transaction.setMoney(transactionDTO.getMoney());
        transaction.setDate(transactionDTO.getDate());
        return null;
    }
}

