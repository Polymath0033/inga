package com.polymath.inga.ingaapi.controllers;

import com.polymath.inga.ingaapi.dto.requests.LoginRequest;
import com.polymath.inga.ingaapi.dto.responses.AuthResponse;
import com.polymath.inga.ingaapi.services.AuthService;
import com.polymath.inga.ingaapi.utils.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest request) {
        AuthResponse response = authService.signUp(request);
        return ResponseHandler.handleResponse(response, HttpStatus.CREATED,"success");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseHandler.handleResponse(response, HttpStatus.OK,"success");
    }
}
