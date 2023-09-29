package com.bopera.pointofsales.auth.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Token {
    private final String tokenType = "bearer";
    private String accessToken;
    private Date expiresIn;
    private String role;
}
