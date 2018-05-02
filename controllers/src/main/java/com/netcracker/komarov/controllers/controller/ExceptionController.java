package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.controllers.exception.DataException;
import com.netcracker.komarov.controllers.exception.ServerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ExceptionController {
    @ResponseStatus
    @ExceptionHandler(value = DataException.class)
    default String handleDataException(DataException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NullPointerException.class)
    default String handleNullPointerException(NullPointerException ex) {
        return "Not found this " + ex.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ServerException.class)
    default String handleServerException(ServerException ex) {
        return ex.getMessage();
    }
}
