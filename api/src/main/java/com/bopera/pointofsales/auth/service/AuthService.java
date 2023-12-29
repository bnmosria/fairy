package com.bopera.pointofsales.auth.service;

import com.bopera.pointofsales.auth.model.request.AuthRequest;
import com.bopera.pointofsales.auth.model.response.AuthResponse;
import com.bopera.pointofsales.domain.model.UserInfoDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthService(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse authenticateAndGetToken(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("invalid user request!");
        }

        return jwtService.generateToken((UserInfoDetails) authentication.getPrincipal());
    }
}
