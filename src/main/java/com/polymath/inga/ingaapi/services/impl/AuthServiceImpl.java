package com.polymath.inga.ingaapi.services.impl;

import com.polymath.inga.ingaapi.dto.requests.LoginRequest;
import com.polymath.inga.ingaapi.dto.responses.AuthResponse;
import com.polymath.inga.ingaapi.exceptions.CustomBadRequest;
import com.polymath.inga.ingaapi.exceptions.CustomNotFound;
import com.polymath.inga.ingaapi.exceptions.UserAlreadyExists;
import com.polymath.inga.ingaapi.models.Users;
import com.polymath.inga.ingaapi.repositories.UserRepo;
import com.polymath.inga.ingaapi.services.AuthService;
import com.polymath.inga.ingaapi.services.JwtService;
import com.polymath.inga.ingaapi.utils.StringUtil;
import com.polymath.inga.ingaapi.utils.TimeUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepo userRepo, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse signUp(LoginRequest request) {
        if (StringUtil.isNullOrEmpty(request.email()) && StringUtil.isNullOrEmpty(request.password())) {
            throw new CustomBadRequest("Invalid email or password");
        }
        Optional<Users> users = userRepo.findByEmail(request.email());
        if (users.isPresent()) {
            throw new UserAlreadyExists(String.format("User with email %s already exists", request.email()));
        }
        Users user = new Users();
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        String token = jwtService.generateToken(request.email());
        LocalDateTime expiration = jwtService.extractExpirationDate(token);
        user.setToken(token);
        user.setTokenExpiration(expiration);
        userRepo.save(user);
        return getAuthResponse(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        if (StringUtil.isNullOrEmpty(request.email()) && StringUtil.isNullOrEmpty(request.password())) {
            throw new CustomBadRequest("Invalid email or password");
        }
        Users existingUser = userRepo.findByEmail(request.email()).orElseThrow(() -> new CustomNotFound(String.format("User with this %s does not exist,please login", request.email())));
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(request.email());
                LocalDateTime expiration = jwtService.extractExpirationDate(token);
                existingUser.setToken(token);
                existingUser.setTokenExpiration(expiration);
                userRepo.save(existingUser);
                return getAuthResponse(existingUser);
            } else {
                throw new CustomBadRequest("Invalid email or password");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private AuthResponse getAuthResponse(Users user) {
        return new AuthResponse(user.getEmail(), user.getToken(), TimeUtil.calculateExpirationTimeInSec(user.getTokenExpiration()));
    }
}
