package com.bopera.pointofsales.auth.presentation;

import com.bopera.pointofsales.auth.model.AuthRequest;
import com.bopera.pointofsales.auth.model.Token;
import com.bopera.pointofsales.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<Token> login(@RequestBody AuthRequest authRequest) {
        Token token;

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
