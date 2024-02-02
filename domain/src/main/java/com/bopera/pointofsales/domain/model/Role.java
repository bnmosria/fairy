package com.bopera.pointofsales.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Role {
    private long id;
    private String name;
    private Set<Permission> permissions;

    public void addPermission(Permission permission) {
        if (permissions == null) {
            permissions = new HashSet<>();
        }
        permissions.add(permission);
    }

    public void removePermission(Permission permission) {
        if (permissions != null) {
            permissions.remove(permission);
        }
    }
}
