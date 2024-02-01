package com.bopera.pointofsales.api.user.model.request;

import com.bopera.pointofsales.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUser implements RequestUser {
    private String username;
    private Integer active;
    private String password;

    @Override
    public User mapToUserDetails() {
        return User.builder()
            .username(getUsername())
            .password(getPassword())
            .active(getActive())
            .build();
    }
}
