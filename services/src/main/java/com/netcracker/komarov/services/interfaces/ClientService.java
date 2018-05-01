package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;

import java.util.Collection;

public interface ClientService {
    ClientDTO signIn(PersonDTO personDTO);

    ClientDTO registration(PersonDTO personDTO);

    Collection<ClientDTO> getAllClients();
}
