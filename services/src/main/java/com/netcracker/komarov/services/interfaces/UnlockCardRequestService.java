package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.UnlockCardRequest;

import java.util.Collection;

public interface UnlockCardRequestService {
    UnlockCardRequest unlockCardRequest(long cardId);

    Collection<UnlockCardRequest> getAllCardRequest();
}
