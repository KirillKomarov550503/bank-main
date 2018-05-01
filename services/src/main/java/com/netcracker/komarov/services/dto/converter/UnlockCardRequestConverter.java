package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.Card;
import com.netcracker.komarov.dao.entity.UnlockCardRequest;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.UnlockCardRequestDTO;

public class UnlockCardRequestConverter implements Converter<UnlockCardRequestDTO, UnlockCardRequest> {
    @Override
    public UnlockCardRequestDTO convertToDTO(UnlockCardRequest request) {
        UnlockCardRequestDTO dto = new UnlockCardRequestDTO();
        Card card = request.getCard();
        dto.setBalance(card.getAccount().getBalance());
        dto.setCardId(card.getCardId());
        dto.setLocked(dto.isLocked());
        dto.setPin(dto.getPin());
        return dto;
    }

    @Override
    public UnlockCardRequest convertToEntity(UnlockCardRequestDTO dto) {
        return null;
    }
}
