package com.bopera.pointofsales.persistence.command;

import com.bopera.pointofsales.persistence.entity.Permission;
import com.bopera.pointofsales.persistence.entity.Role;
import com.bopera.pointofsales.persistence.entity.User;
import com.bopera.pointofsales.persistence.repository.PermissionRepository;
import com.bopera.pointofsales.persistence.repository.RoleRepository;
import com.bopera.pointofsales.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Set;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CommandLineAppStartupRunner(
        UserRepository userRepository,
        PermissionRepository permissionRepository,
        RoleRepository roleRepository,
        PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            Permission adminPermission = permissionRepository.findByPermissionName("ADMIN_ACCESS").orElseGet(() -> {
                Permission permission = new Permission();
                permission.setPermissionName("ADMIN_ACCESS");
                return permissionRepository.save(permission);
            });

            Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN").orElseGet(() -> {
                Role role = new Role();
                role.setRoleName("ROLE_ADMIN");
                role.setPermissions(Set.of(adminPermission));

                return roleRepository.save(role);
            });

            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setActive(1);
            adminUser.setPassword(passwordEncoder.encode("secret"));
            adminUser.setRoles(Set.of(adminRole));
            userRepository.save(adminUser);
        }
    }
}
