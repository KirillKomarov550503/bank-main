package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;

import java.util.Collection;

public interface ClientService {
    ClientDTO signIn(PersonDTO personDTO);

    ClientDTO save(PersonDTO personDTO);

    Collection<ClientDTO> findAllClients();

    ClientDTO update(ClientDTO clientDTO, long clientId);

    void deleteById(long clientId);

    ClientDTO findById(long clientId);
}
