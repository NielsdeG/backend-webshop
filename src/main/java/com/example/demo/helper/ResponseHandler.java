package com.example.demo.helper;

import com.example.demo.model.Role;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    private ResponseHandler() {
        throw new IllegalStateException("Utility class");
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObject) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObject);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateTokenResponse(String message, HttpStatus status, String token, String name, Role role){
        Map<String, Object> map = new HashMap<>();
        JSONObject obj = new JSONObject();
        obj.put("token", token);
        obj.put("name", name);
        obj.put("role", role);
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", obj.toMap());

        return new ResponseEntity<>(map, status);
    }

}