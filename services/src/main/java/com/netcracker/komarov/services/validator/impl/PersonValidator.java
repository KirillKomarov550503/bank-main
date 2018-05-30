package com.netcracker.komarov.services.validator.impl;

import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.ValidationException;
import com.netcracker.komarov.services.validator.ValidationType;
import com.netcracker.komarov.services.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PersonValidator implements Validator<PersonDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonValidator.class);

    private boolean isValidData(String input, ValidationType type) {
        String regex;
        switch (type) {
            case NAME:
                regex = "^[A-Za-z]{1,30}-?[A-Za-z]{0,30}$";
                break;
            case USERNAME:
                regex = "^[A-Za-z0-9_]{6,30}$";
                break;
            case PASSWORD:
                regex = "^[A-Za-z0-9]{8,30}$";
                break;
            default:
                regex = "";
                break;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    @Override
    public void validate(PersonDTO personDTO) throws ValidationException {
        if (!isValidData(personDTO.getName(), ValidationType.NAME)) {
            String error = "Wrong input name";
            LOGGER.error(error);
            throw new ValidationException(error);
        }
        if (!isValidData(personDTO.getSurname(), ValidationType.NAME)) {
            String error = "Wrong input surname";
            LOGGER.error(error);
            throw new ValidationException(error);
        }
        if (!isValidData(personDTO.getUsername(), ValidationType.USERNAME)) {
            String error = "Wrong input username";
            LOGGER.error(error);
            throw new ValidationException(error);
        }
        if (!isValidData(personDTO.getPassword(), ValidationType.PASSWORD)) {
            String error = "Wrong input password";
            LOGGER.error(error);
            throw new ValidationException(error);
        }
    }
}
