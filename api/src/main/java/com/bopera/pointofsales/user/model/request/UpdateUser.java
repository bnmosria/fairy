package com.bopera.pointofsales.user.model.request;

import com.bopera.pointofsales.domain.model.UserDetails;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser extends CreateUser implements RequestUser {
    private long id;

    @Override
    public UserDetails mapToUserDetails() {
        return UserDetails.builder()
            .username(getUsername())
            .active(getActive())
            .id(getId())
            .build();
    }
}
