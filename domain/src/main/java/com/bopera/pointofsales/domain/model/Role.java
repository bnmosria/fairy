package com.bopera.pointofsales.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Role {
    private String name;
    private Set<Permission> permissions;
}
