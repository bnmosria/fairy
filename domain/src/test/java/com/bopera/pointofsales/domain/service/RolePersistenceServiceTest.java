package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.Permission;
import com.bopera.pointofsales.domain.model.Role;
import com.bopera.pointofsales.persistence.entity.RoleEntity;
import com.bopera.pointofsales.persistence.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolePersistenceServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RolePersistence rolePersistenceService;

    private Role roleWithPermissions;
    private Role roleWithoutPermissions;

    @BeforeEach
    void setUp() {
        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.builder().name("READ").build());
        permissions.add(Permission.builder().name("WRITE").build());

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(123L);
        roleEntity.setRoleName("Fooo");

        doReturn(roleEntity).when(roleRepository).save(any());

        roleWithPermissions = Role.builder()
                .name("ADMIN")
                .permissions(permissions)
                .build();

        roleWithoutPermissions = Role.builder()
                .name("USER")
                .build();
    }

    @Test
    void ShouldSaveRoleEntity_WhenRoleWithPermissionsSaved() {
        rolePersistenceService.addRole(roleWithPermissions);
        verify(roleRepository, times(1)).save(any(RoleEntity.class));
    }

    @Test
    void ShouldSaveRoleEntity_WhenRoleWithNoPermissionsSaved() {
        rolePersistenceService.addRole(roleWithoutPermissions);
        verify(roleRepository, times(1)).save(any(RoleEntity.class));
    }

    @Test
    void ShouldSaveRoleEntityWithMultiplePermissions_WhenRoleWithMultiplePermissionsSaved() {
        rolePersistenceService.addRole(roleWithPermissions);
        verify(roleRepository, times(1)).save(any(RoleEntity.class));
    }
}
