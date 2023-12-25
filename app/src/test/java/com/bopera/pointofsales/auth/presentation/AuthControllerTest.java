package com.bopera.pointofsales.auth.presentation;

import com.bopera.pointofsales.auth.filter.JwtAuthFilter;
import com.bopera.pointofsales.auth.model.AuthRequest;
import com.bopera.pointofsales.auth.model.Jwt;
import com.bopera.pointofsales.auth.service.AuthService;
import com.bopera.pointofsales.auth.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = AuthController.class)
class AuthControllerTest {
    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private AuthService authService;

    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(authService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testLoginWithValidCredentials() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        Jwt jwt = Jwt.builder().build();

        when(authService.authenticateAndGetToken(authRequest)).thenReturn(jwt);

        ResponseEntity<Jwt> response = authController.login(authRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jwt, response.getBody());

        verify(authService, times(1)).authenticateAndGetToken(authRequest);
    }

    @Test
    void testLoginWithInvalidCredentials() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        when(authService.authenticateAndGetToken(authRequest))
            .thenThrow(new BadCredentialsException("Invalid credentials"));

        ResponseEntity<Jwt> response = authController.login(authRequest);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        verify(authService, times(1))
            .authenticateAndGetToken(authRequest);
    }

    @Test
    void testLoginWithNullToken() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        when(authService.authenticateAndGetToken(authRequest)).thenReturn(null);

        ResponseEntity<Jwt> response = authController.login(authRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(authService, times(1))
            .authenticateAndGetToken(authRequest);

    }

    @Test
    void shouldReturnTokenWhenValidAuthRequestGiven() throws Exception {
        AuthRequest authRequest = new AuthRequest("username", "password");
        Jwt token = Jwt.builder().accessToken("accessToken").expiresIn(new Date()).build();

        doReturn(token).when(authService).authenticateAndGetToken(authRequest);

        mockMvc.perform(post("/api/auth/token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\":\"username\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"));
    }

    @Test
    void shouldReturnBadRequestResponseWhenInvalidAuthRequest() throws Exception {
        mockMvc.perform(post("/api/auth/token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\":\"\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
