package com.bopera.pointofsales.auth.service;

import com.bopera.pointofsales.auth.model.request.AuthRequest;
import com.bopera.pointofsales.auth.model.response.AuthResponse;
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
        AuthResponse expectedToken = AuthResponse.builder().build();
        doReturn(expectedToken).when(jwtService).generateToken(any());
        doReturn(authentication).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        doReturn(true).when(authentication).isAuthenticated();

        AuthResponse actualToken = authService.authenticateAndGetToken(mock(AuthRequest.class));

        assertThat(actualToken).isEqualTo(expectedToken);
    }

    @Test
    public void shouldThrowBadCredentialsExceptionWhenUserCanNotAuthenticate() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.isAuthenticated()).thenReturn(false);

        assertThatThrownBy(() -> authService.authenticateAndGetToken(mock(AuthRequest.class)))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("invalid user request!");
    }
}
