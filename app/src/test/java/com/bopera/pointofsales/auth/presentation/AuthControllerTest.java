package com.bopera.pointofsales.auth.presentation;

import com.bopera.pointofsales.auth.filter.JwtAuthFilter;
import com.bopera.pointofsales.auth.model.AuthRequest;
import com.bopera.pointofsales.auth.model.Jwt;
import com.bopera.pointofsales.auth.service.AuthService;
import com.bopera.pointofsales.auth.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = AuthController.class)
public class AuthControllerTest {
    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private AuthService authService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        AuthController authController = new AuthController(authService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void shouldReturnTokenWhenValidAuthRequestGiven() throws Exception {
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
    public void shouldReturnBadRequestResponseWhenInvalidAuthRequest() throws Exception {
        mockMvc.perform(post("/api/auth/token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\":\"\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
