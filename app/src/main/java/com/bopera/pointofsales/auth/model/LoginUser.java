package com.bopera.pointofsales.auth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUser {
    String name;
}
