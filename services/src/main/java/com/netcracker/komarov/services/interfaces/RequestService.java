package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.Request;
import com.netcracker.komarov.dao.entity.RequestStatus;

import java.util.Collection;

public interface RequestService {
    Request saveRequest(long requestId, RequestStatus requestStatus);

    Request findById(long id);

    void delete(long id);

    Collection<Request> findAllRequests();
}
