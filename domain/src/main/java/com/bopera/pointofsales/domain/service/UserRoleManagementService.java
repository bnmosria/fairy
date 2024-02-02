package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.Role;
import com.bopera.pointofsales.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserRoleManagementService {
    private final PersistenceUserService persistenceUserService;

    public UserRoleManagementService(PersistenceUserService persistenceUserService) {
        this.persistenceUserService = persistenceUserService;
    }

    public void addRoleToUser(User user, Role role) {
        user.addRole(role);
        persistenceUserService.save(user);
    }

    public void removeRoleFromUser(User user, Role role) {
        user.removeRole(role);
        persistenceUserService.save(user);
    }

    public Set<Role> getUserRoles(User user) {
        return user.getRoles();
    }
}
