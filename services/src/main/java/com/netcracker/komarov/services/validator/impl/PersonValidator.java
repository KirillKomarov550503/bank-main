package com.netcracker.komarov.services.validator.impl;

import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.ValidationException;
import com.netcracker.komarov.services.validator.ValidationType;
import com.netcracker.komarov.services.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PersonValidator implements Validator<PersonDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonValidator.class);

    @Override
    public void validate(PersonDTO personDTO) throws ValidationException {
        if (!isMatchesRegex(personDTO.getName(), ValidationType.NAME)) {
            String error = "Wrong input name";
            LOGGER.error(error);
            throw new ValidationException(error);
        }
        if (!isMatchesRegex(personDTO.getSurname(), ValidationType.NAME)) {
            String error = "Wrong input surname";
            LOGGER.error(error);
            throw new ValidationException(error);
        }
        if (!isMatchesRegex(personDTO.getUsername(), ValidationType.USERNAME)) {
            String error = "Wrong input username";
            LOGGER.error(error);
            throw new ValidationException(error);
        }
        if (!isMatchesRegex(personDTO.getPassword(), ValidationType.PASSWORD)) {
            String error = "Wrong input password";
            LOGGER.error(error);
            throw new ValidationException(error);
        }
    }
}
