package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.Role;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleAdmin);
}
