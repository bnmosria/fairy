package com.bopera.pointofsales.user.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserResponse {
    String name;
}
