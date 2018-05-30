package com.netcracker.komarov.services.util;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UriBuilder {
    public String build(String uri, Map<String, String> params) {
        uri += "?";
        StringBuilder uriBuilder = new StringBuilder(uri);
        for (Map.Entry<String, String> map : params.entrySet()) {
            uriBuilder.append(map.getKey()).append("=").append(map.getValue()).append("&");
        }
        uri = uriBuilder.toString();
        return uri;
    }
}
