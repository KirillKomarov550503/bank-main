package com.netcracker.komarov.services.validator.impl;

import com.netcracker.komarov.services.dto.entity.CardDTO;
import com.netcracker.komarov.services.exception.ValidationException;
import com.netcracker.komarov.services.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CardValidator implements Validator<CardDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardValidator.class);

    @Override
    public void validate(CardDTO cardDTO) throws ValidationException {
        Pattern pattern = Pattern.compile("^[0-9]{4,8}$");
        Matcher matcher = pattern.matcher(cardDTO.getPin());
        if (!matcher.matches()) {
            String error = "Wrong input pin";
            LOGGER.error(error);
            throw new ValidationException(error);
        }
    }
}
