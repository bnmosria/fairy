package com.bopera.pointofsales.auth.service;

import com.bopera.pointofsales.auth.model.Jwt;
import com.bopera.pointofsales.model.UserInfoDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private JwtService jwtService;

    private String userName;

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService("0110001111000011101001010101011111100000000011110010101111000011");
        userName = "testUser";
    }

    @Test
    public void shouldGenerateTokenWithProvidedUserName() {
        Jwt token = jwtService.generateToken(UserInfoDetails.builder()
                .name(userName)
                .password("foo")
                .authorities(Collections.emptyList())
                .build());

        assertThat(token.getAccessToken()).isNotNull();
        assertThat(token.getExpiresIn()).isAfterOrEqualTo(new Date());
        assertThat(jwtService.extractUsername(token.getAccessToken())).isEqualTo(userName);
    }
}
