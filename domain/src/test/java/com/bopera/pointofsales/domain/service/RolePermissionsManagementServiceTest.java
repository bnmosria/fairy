package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.Permission;
import com.bopera.pointofsales.domain.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class RolePermissionsManagementServiceTest {

    @Mock
    private RolePersistence rolePersistenceService;

    private RolePermissionsManagementService rolePermissionsManagementService;

    private Role role;
    private Permission permission;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rolePermissionsManagementService = new RolePermissionsManagementService(rolePersistenceService);
        role = Role.builder().name("TestRole").permissions(new HashSet<>()).build();
        permission = Permission.builder().name("TestPermission").build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(rolePersistenceService);
    }

    @Test
    void ShouldAddPermissionAndSaveRole_WhenPermissionAddedToRole() {
        rolePermissionsManagementService.addPermissionToRole(role, permission);

        verify(rolePersistenceService, times(1)).addRole(role);
        assertEquals(1, role.getPermissions().size());
        assertTrue(role.getPermissions().contains(permission));
    }

    @Test
    void ShouldRemovePermissionAndSaveRole_WhenPermissionRemovedFromRole() {
        role.addPermission(permission);

        rolePermissionsManagementService.removePermissionFromRole(role, permission);
        verify(rolePersistenceService, times(1)).addRole(role);
        assertEquals(0, role.getPermissions().size());
    }

    @Test
    void ShouldReturnPermission_WhenRoleHasPermission() {
        role.addPermission(permission);

        Set<Permission> permissions = rolePermissionsManagementService.getRolePermissions(role);
        assertEquals(1, permissions.size());
        assertTrue(permissions.contains(permission));
    }
}
