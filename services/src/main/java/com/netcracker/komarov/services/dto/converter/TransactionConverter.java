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
            dto.setAccountFromId(transaction.getAccountFromId());
            dto.setAccountToId(transaction.getAccountToId());
            dto.setDate(transaction.getDate());
            dto.setMoney(transaction.getMoney());
        }
        return dto;
    }

    @Override
    public Transaction convertToEntity(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setAccountFromId(dto.getAccountFromId());
        transaction.setAccountToId(dto.getAccountToId());
        transaction.setDate(dto.getDate());
        transaction.setMoney(dto.getMoney());
        return transaction;
    }
}
