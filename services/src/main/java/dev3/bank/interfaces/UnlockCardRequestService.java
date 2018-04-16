package dev3.bank.interfaces;

import dev3.bank.entity.UnlockCardRequest;

import java.util.Collection;

public interface UnlockCardRequestService extends BaseService {
    UnlockCardRequest unlockCardRequest(long cardId);

    Collection<UnlockCardRequest> getAllCardRequest();
}
