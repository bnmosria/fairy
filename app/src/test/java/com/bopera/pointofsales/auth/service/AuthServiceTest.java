package com.bopera.pointofsales.auth.service;

import com.bopera.pointofsales.auth.model.AuthRequest;
import com.bopera.pointofsales.auth.model.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    private AuthService authService;

    @BeforeEach
    public void setUp() {
        authService = new AuthService(jwtService, authenticationManager);
    }

    @Test
    public void shouldReturnATokenWhenUserIsAuthenticated() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        Token expectedToken = Token.builder().build();
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(authRequest.getUsername())).thenReturn(expectedToken);

        Token actualToken = authService.authenticateAndGetToken(authRequest);

        assertThat(actualToken).isEqualTo(expectedToken);
        verify(jwtService).generateToken(authRequest.getUsername());
    }

    @Test
    public void shouldThrowBadCredentialsExceptionWhenUserCanNotAuthenticate() {
        AuthRequest authRequest = new AuthRequest("username", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.isAuthenticated()).thenReturn(false);

        assertThatThrownBy(() -> authService.authenticateAndGetToken(authRequest))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("invalid user request!");
    }
}