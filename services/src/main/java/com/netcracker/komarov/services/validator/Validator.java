package com.netcracker.komarov.services.validator;

import com.netcracker.komarov.services.exception.ValidationException;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Validator<DTO extends Serializable> {
    void validate(DTO dto) throws ValidationException;

    default boolean isMatchesRegex(String input, ValidationType type) {
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
}
