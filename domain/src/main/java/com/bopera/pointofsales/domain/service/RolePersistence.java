package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.exception.DuplicatedRoleException;
import com.bopera.pointofsales.domain.exception.DuplicatedUserNameException;
import com.bopera.pointofsales.domain.interfaces.RoleService;
import com.bopera.pointofsales.domain.model.Permission;
import com.bopera.pointofsales.domain.model.Role;
import com.bopera.pointofsales.persistence.entity.PermissionEntity;
import com.bopera.pointofsales.persistence.entity.RoleEntity;
import com.bopera.pointofsales.persistence.entity.UserEntity;
import com.bopera.pointofsales.persistence.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolePersistence implements RoleService {
    private final RoleRepository roleRepository;

    public RolePersistence(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role addRole(Role role) {
        if (roleRepository.findByRoleName(role.getName()).isPresent()) {
            throw new DuplicatedRoleException(
                "The role already exists"
            );
        }

        RoleEntity savedRoleEntity = roleRepository.save(mapToRoleEntity(role));

        return Role.builder()
            .id(savedRoleEntity.getId())
            .name(savedRoleEntity.getRoleName())
            .build();
    }


    @Override
    public Role getRoleById(String roleId) {
        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        return null;
    }

    @Override
    public Role updateRole(Role role) {
        return null;
    }

    @Override
    public void deleteRole(String roleId) {

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
