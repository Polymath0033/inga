package com.polymath.inga.ingaapi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<?> handleResponse(Object responseData, HttpStatus status,String message) {
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        if(responseData!=null)response.put("data", responseData);
        return new ResponseEntity<>(response, status);
    }
}
