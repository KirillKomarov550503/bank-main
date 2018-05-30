package com.netcracker.komarov.services.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MapConverter {
    public Map<String, String> convert(Map<String, String> params) {
        Map<String, String> map = new HashMap<>();
        String[] par = params.get("parameters").split("&");
        for (String string : par) {
            String[] pair = string.split("=");
            map.put(pair[0], pair[1]);
        }
        return map;
    }
}
