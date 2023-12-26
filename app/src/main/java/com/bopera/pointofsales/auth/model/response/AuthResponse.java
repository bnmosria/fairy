package com.bopera.pointofsales.auth.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AuthResponse {
    private final String tokenType = "bearer";
    private String accessToken;
    private Date expiresIn;
    private String roles;
}
