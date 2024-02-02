package com.bopera.pointofsales.api.roles.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleRequest {
    @NotBlank
    @Size(min = 3)
    private String name;
}
