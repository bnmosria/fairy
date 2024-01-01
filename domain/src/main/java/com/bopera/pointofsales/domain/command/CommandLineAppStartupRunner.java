package com.bopera.pointofsales.domain.command;

import com.bopera.pointofsales.persistence.entity.PermissionEntity;
import com.bopera.pointofsales.persistence.entity.RoleEntity;
import com.bopera.pointofsales.persistence.entity.UserEntity;
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
            PermissionEntity adminPermission = permissionRepository.findByPermissionName("administer").orElseGet(() -> {
                PermissionEntity permission = new PermissionEntity();
                permission.setPermissionName("administer");
                return permissionRepository.save(permission);
            });

            RoleEntity adminRole = roleRepository.findByRoleName("ROLE_ADMIN").orElseGet(() -> {
                RoleEntity role = new RoleEntity();
                role.setRoleName("ROLE_ADMIN");

                Set.of(adminPermission).forEach(role::addPermission);

                return roleRepository.save(role);
            });

            UserEntity adminUser = new UserEntity();
            adminUser.setUsername("admin");
            adminUser.setActive(1);
            adminUser.setPassword(passwordEncoder.encode("secret"));

            Set.of(adminRole).forEach(adminUser::addRole);

            userRepository.save(adminUser);
        }
    }
}
