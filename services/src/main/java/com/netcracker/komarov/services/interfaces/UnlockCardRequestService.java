package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.UnlockCardRequestDTO;

import java.util.Collection;

public interface UnlockCardRequestService {
    UnlockCardRequestDTO addCardRequest(long cardId);

    Collection<UnlockCardRequestDTO> getAllCardRequest();
}
