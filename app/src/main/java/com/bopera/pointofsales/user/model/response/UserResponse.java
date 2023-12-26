package com.bopera.pointofsales.user.model.response;

import com.bopera.pointofsales.persistence.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String userName;
    private int active;
    private Set<String> roles;

    public UserResponse(User user) {
        id = user.getId();
        userName = user.getUsername();
        active = user.getActive();
    }
}
