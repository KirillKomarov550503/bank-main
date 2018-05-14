package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter implements Converter<AccountDTO, Account> {
    @Override
    public AccountDTO convertToDTO(Account account) {
        AccountDTO dto = null;
        if (account != null) {
            dto = new AccountDTO();
            dto.setLocked(account.isLocked());
            dto.setId(account.getId());
            dto.setBalance(account.getBalance());
            dto.setClientId(account.getClient().getId());
        }
        return dto;
    }

    @Override
    public Account convertToEntity(AccountDTO dto) {
        Account account = new Account();
        account.setLocked(dto.isLocked());
        account.setBalance(dto.getBalance());
        return account;
    }
}
