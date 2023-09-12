package com.bopera.pointofsales.auth.service;

import com.bopera.pointofsales.auth.model.AuthRequest;
import com.bopera.pointofsales.auth.model.Token;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class AuthService {
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthService(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Token authenticateAndGetToken(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("invalid user request!");
        }

        return jwtService.generateToken(authRequest.getUsername());
    }
}