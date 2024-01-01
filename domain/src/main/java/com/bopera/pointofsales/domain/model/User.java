package com.bopera.pointofsales.domain.model;

import com.bopera.pointofsales.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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

    public User(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = user.getActive();

        user.getRoles().forEach(role -> {
                Role newRole = Role.builder()
                    .name(role.getRoleName())
                    .build();

                this.roles.add(newRole);
            });
    }
}
