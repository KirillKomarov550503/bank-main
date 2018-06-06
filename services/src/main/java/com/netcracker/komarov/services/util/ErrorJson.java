package com.netcracker.komarov.services.util;

import org.springframework.stereotype.Component;

@Component
public class ErrorJson {
    public String getErrorJson(String error) {
        return "{\"error\":\"" + error + "\"}";
    }
}
