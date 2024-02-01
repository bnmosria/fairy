package com.bopera.pointofsales.api.user.model.response;

import com.bopera.pointofsales.domain.model.User;
import lombok.*;

import java.util.Set;

@Data
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
