package com.bopera.pointofsales.domain.model;

import com.bopera.pointofsales.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private Integer active;
    private final Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }
    public User withRolesAndPermissions(UserEntity userEntity) {
        userEntity.getRoles().forEach(role -> {
            Role newRole = Role.builder()
                .name(role.getRoleName())
                .permissions(
                    role.getPermissions().stream().map(
                        permission ->
                            Permission.builder()
                                .name(permission.getPermissionName())
                                .build()
                        ).collect(Collectors.toSet()
                    )
                )
                .build();

            this.addRole(newRole);
        });

        return this;
    }

}
