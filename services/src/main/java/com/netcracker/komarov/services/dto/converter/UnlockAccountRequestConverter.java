package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.UnlockAccountRequest;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.UnlockAccountRequestDTO;

public class UnlockAccountRequestConverter implements Converter<UnlockAccountRequestDTO, UnlockAccountRequest> {
    @Override
    public UnlockAccountRequestDTO convertToDTO(UnlockAccountRequest request) {
        UnlockAccountRequestDTO dto = new UnlockAccountRequestDTO();
        Account account = request.getAccount();
        dto.setLocked(account.isLocked());
        dto.setAccountId(account.getAccountId());
        dto.setBalance(account.getBalance());
        return dto;
    }

    @Override
    public UnlockAccountRequest convertToEntity(UnlockAccountRequestDTO dto) {
        return null;
    }
}
