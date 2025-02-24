package com.polymath.inga.ingaapi.dto.responses;

public record AuthResponse(String email, String token, long expiresIn) {
}
