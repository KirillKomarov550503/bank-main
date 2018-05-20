package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;

import java.util.Collection;

public interface ClientService {
    ClientDTO save(PersonDTO personDTO) throws LogicException;

    Collection<ClientDTO> findAllClients();

    ClientDTO update(ClientDTO clientDTO) throws NotFoundException;

    void deleteById(long clientId) throws NotFoundException;

    ClientDTO findById(long clientId) throws NotFoundException;
}
