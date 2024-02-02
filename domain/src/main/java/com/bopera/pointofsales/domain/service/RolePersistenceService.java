package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.Permission;
import com.bopera.pointofsales.domain.model.Role;
import com.bopera.pointofsales.persistence.entity.PermissionEntity;
import com.bopera.pointofsales.persistence.entity.RoleEntity;
import com.bopera.pointofsales.persistence.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RolePersistenceService {
    private final RoleRepository roleRepository;

    public RolePersistenceService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void saveRole(Role role) {
        RoleEntity roleEntity = mapToRoleEntity(role);
        roleRepository.save(roleEntity);
    }

    private RoleEntity mapToRoleEntity(Role role) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleName(role.getName());

        if (role.getPermissions() != null) {
            for (Permission permission : role.getPermissions()) {
                PermissionEntity permissionEntity = new PermissionEntity();
                permissionEntity.setPermissionName(permission.getName());

                roleEntity.addPermission(permissionEntity);
            }
        }

        return roleEntity;
    }
}
