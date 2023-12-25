package com.bopera.pointofsales.auth.presentation;

import com.bopera.pointofsales.auth.model.AuthRequest;
import com.bopera.pointofsales.auth.model.Jwt;
import com.bopera.pointofsales.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Jwt> login(@RequestBody AuthRequest authRequest) {
        Jwt token;

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
