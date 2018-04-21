package dev3.bank.interfaces;

import dev3.bank.entity.UnlockAccountRequest;

import java.util.Collection;

public interface UnlockAccountRequestService {
    UnlockAccountRequest unlockAccountRequest(long accountId);

    Collection<UnlockAccountRequest> getAllAccountRequest();
}
