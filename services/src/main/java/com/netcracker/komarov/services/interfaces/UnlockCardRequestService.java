package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.UnlockCardRequest;

import java.util.Collection;

public interface UnlockCardRequestService {
    UnlockCardRequest addCardRequest(long cardId);

    Collection<UnlockCardRequest> getAllCardRequest();
}
