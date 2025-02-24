package com.polymath.inga.ingaapi.services;

import com.polymath.inga.ingaapi.dto.requests.LoginRequest;
import com.polymath.inga.ingaapi.dto.responses.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthResponse signUp(LoginRequest request);
    AuthResponse login(LoginRequest request);
}
