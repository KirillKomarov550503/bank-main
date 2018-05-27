package com.netcracker.komarov.services.exception;

public class ExternalServiceException extends RuntimeException {
    private int status;

    public ExternalServiceException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
