package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.NotFoundException;

import java.util.Collection;

public interface PersonService {
    Collection<PersonDTO> findAllPeople();

    PersonDTO findById(long personId) throws NotFoundException;

    PersonDTO save(PersonDTO personDTO);

    PersonDTO update(PersonDTO personDTO);

    void deleteById(long personId);
}
