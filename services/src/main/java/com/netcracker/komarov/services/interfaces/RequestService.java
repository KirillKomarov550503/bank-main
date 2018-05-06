package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.RequestStatus;
import com.netcracker.komarov.services.dto.entity.RequestDTO;

import java.util.Collection;

public interface RequestService {
    RequestDTO saveRequest(long requestId, RequestStatus requestStatus);

    RequestDTO findById(long id);

    void delete(long id);

    Collection<RequestDTO> findAllRequests();
}
