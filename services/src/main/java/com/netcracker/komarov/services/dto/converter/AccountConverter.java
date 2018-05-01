package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter implements Converter<AccountDTO, Account> {
    @Override
    public AccountDTO convertToDTO(Account account) {
        return new AccountDTO(account.isLocked(), account.getBalance(), account.getAccountId());
    }

    @Override
    public Account convertToEntity(AccountDTO dto) {
        Account account = new Account();
        account.setLocked(dto.isLocked());
        account.setBalance(dto.getBalance());
        account.setAccountId(dto.getAccountId());
        return account;
    }
}
