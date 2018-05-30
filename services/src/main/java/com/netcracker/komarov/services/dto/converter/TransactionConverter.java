package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.Transaction;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.TransactionDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter implements Converter<TransactionDTO, Transaction> {
    @Override
    public TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = null;
        if (transaction != null) {
            dto = new TransactionDTO();
            dto.setId(transaction.getId());
            dto.setAccountFromId(String.valueOf(transaction.getAccountFromId()));
            dto.setAccountToId(String.valueOf(transaction.getAccountToId()));
            dto.setDate(transaction.getDate());
            dto.setMoney(String.valueOf(transaction.getMoney()));
        }
        return dto;
    }

    @Override
    public Transaction convertToEntity(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setAccountFromId(Integer.valueOf(dto.getAccountFromId()));
        transaction.setAccountToId(Integer.valueOf(dto.getAccountToId()));
        transaction.setMoney(Double.valueOf(dto.getMoney()));
        return transaction;
    }
}
