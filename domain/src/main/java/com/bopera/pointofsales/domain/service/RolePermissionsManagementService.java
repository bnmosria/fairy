package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.Permission;
import com.bopera.pointofsales.domain.model.Role;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RolePermissionsManagementService {
    private final RolePersistenceService rolePersistenceService;

    public RolePermissionsManagementService(RolePersistenceService rolePersistenceService) {
        this.rolePersistenceService = rolePersistenceService;
    }

    public void addPermissionToRole(Role role, Permission permission) {
        role.addPermission(permission);
        rolePersistenceService.saveRole(role);
    }

    public void removePermissionFromRole(Role role, Permission permission) {
        role.removePermission(permission);
        rolePersistenceService.saveRole(role);
    }

    public Set<Permission> getRolePermissions(Role role) {
        return role.getPermissions();
    }
}
