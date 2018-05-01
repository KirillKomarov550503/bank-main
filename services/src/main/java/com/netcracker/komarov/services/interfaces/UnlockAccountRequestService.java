package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.UnlockAccountRequestDTO;

import java.util.Collection;

public interface UnlockAccountRequestService {
    UnlockAccountRequestDTO addAccountRequest(long accountId);

    Collection<UnlockAccountRequestDTO> getAllAccountRequest();
}
