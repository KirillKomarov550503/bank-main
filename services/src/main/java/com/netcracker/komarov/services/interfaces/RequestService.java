package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.RequestStatus;
import com.netcracker.komarov.services.dto.entity.RequestDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;

import java.util.Collection;

public interface RequestService {
    RequestDTO saveRequest(long requestId, RequestStatus requestStatus) throws LogicException, NotFoundException;

    RequestDTO findById(long id) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    Collection<RequestDTO> findAllRequests();
}
