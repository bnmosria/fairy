package com.bopera.pointofsales.auth.controller;

import com.bopera.pointofsales.auth.model.request.AuthRequest;
import com.bopera.pointofsales.auth.model.response.AuthResponse;
import com.bopera.pointofsales.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        AuthResponse token;

        try {
            token = authService.authenticateAndGetToken(authRequest);
        } catch (BadCredentialsException badCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (null == token) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(token);
    }
}
