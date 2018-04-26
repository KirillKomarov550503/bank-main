package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.UnlockAccountRequest;

import java.util.Collection;

public interface UnlockAccountRequestService {
    UnlockAccountRequest unlockAccountRequest(long accountId);

    Collection<UnlockAccountRequest> getAllAccountRequest();
}
