package com.springframework.spring6webapp.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> responseBuilder(
            String message, HttpStatus httpStatus, HttpHeaders httpHeaders, Object object){
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("httpStatus", httpStatus);
        response.put("httpHeaders", httpHeaders);
        response.put("object", object);

        return new ResponseEntity<>(response, httpStatus);
    }

    public static ResponseEntity<Object> responseBuilder(
            String message, HttpStatus httpStatus, HttpHeaders httpHeaders){
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("httpStatus", httpStatus);
        response.put("httpHeaders", httpHeaders);

        return new ResponseEntity<>(response, httpStatus);
    }
    public static ResponseEntity<Object> responseBuilder(
            String message, HttpStatus httpStatus){
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("httpStatus", httpStatus);

        return new ResponseEntity<>(response, httpStatus);
    }
}

