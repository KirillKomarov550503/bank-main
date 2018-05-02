package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonConverter implements Converter<PersonDTO, Person> {
    @Override
    public PersonDTO convertToDTO(Person person) {
        PersonDTO dto = null;
        if (person != null) {
            dto = new PersonDTO();
            dto.setName(person.getName());
            dto.setSurname(person.getSurname());
            dto.setPassportId(person.getPassportId());
            dto.setPhoneNumber(person.getPhoneNumber());
        }
        return dto;
    }

    @Override
    public Person convertToEntity(PersonDTO dto) {
        Person person = new Person();
        person.setName(dto.getName());
        person.setSurname(dto.getSurname());
        person.setPassportId(dto.getPassportId());
        person.setPhoneNumber(dto.getPhoneNumber());
        return person;
    }
}