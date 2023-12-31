package com.bopera.pointofsales.domain.model;

import com.bopera.pointofsales.persistence.entity.Role;
import com.bopera.pointofsales.persistence.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    private Long id;
    private String username;
    private String password;
    private Integer active;
    private Set<Role> roles;

    public UserDetails (User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = user.getActive();
    }
}
