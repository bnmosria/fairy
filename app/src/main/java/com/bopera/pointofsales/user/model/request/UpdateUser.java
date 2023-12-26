package com.bopera.pointofsales.user.model.request;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser extends CreateUser {
    private long id;
}
