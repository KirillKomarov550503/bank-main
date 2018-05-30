package com.netcracker.komarov.services.validator;

import com.netcracker.komarov.services.exception.ValidationException;

import java.io.Serializable;

public interface Validator<DTO extends Serializable> {
    void validate(DTO dto) throws ValidationException;
}
