package com.netcracker.komarov.services.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    private static final ErrorDecoder ERROR_DECODER = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        System.err.println("Reason: " + response.reason());
        System.err.println("Body: " + response.body());
        System.err.println("Headers: " + response.headers());
        System.err.println("Request: " + response.request());
        System.err.println("Builder: " + response.toBuilder());
        System.err.println("S: " + methodKey);
        return ERROR_DECODER.decode(methodKey, response);
    }
}
