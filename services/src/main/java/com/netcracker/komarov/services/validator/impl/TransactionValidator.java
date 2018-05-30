package com.netcracker.komarov.services.validator.impl;

import com.netcracker.komarov.services.dto.entity.TransactionDTO;
import com.netcracker.komarov.services.exception.ValidationException;
import com.netcracker.komarov.services.validator.ValidationType;
import com.netcracker.komarov.services.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TransactionValidator implements Validator<TransactionDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionValidator.class);

    private boolean isValidData(String input, ValidationType type) {
        String regex;
        switch (type) {
            case ID:
                regex = "^\\d{13}$";
                break;
            case MONEY:
                regex = "^\\d+.\\d{2}$";
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
    public void validate(TransactionDTO transactionDTO) throws ValidationException {

    }
}
