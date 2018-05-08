package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.PersonDTO;

import java.util.Collection;

public interface PersonService {
    Collection<PersonDTO> getAllPeople();

    PersonDTO findById(long personId);
}
