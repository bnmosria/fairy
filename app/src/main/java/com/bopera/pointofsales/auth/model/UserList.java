package com.bopera.pointofsales.auth.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserList {
    List<String> names;
}
