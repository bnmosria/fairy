package com.bopera.pointofsales.api.roles.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EditRoleRequest extends RoleRequest {
    @NotNull
    @Min(1)
    private long id;
}
