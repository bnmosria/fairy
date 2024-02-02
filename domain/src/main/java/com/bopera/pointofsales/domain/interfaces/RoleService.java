package com.bopera.pointofsales.domain.interfaces;

import com.bopera.pointofsales.domain.model.Role;
import java.util.List;

public interface RoleService {
    Role addRole(Role role);

    Role getRoleById(String roleId);

    List<Role> getAllRoles();

    Role updateRole(Role role);

    void deleteRole(String roleId);
}
