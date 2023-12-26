package com.bopera.pointofsales.persistence.repository;

import com.bopera.pointofsales.persistence.entity.Role;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleAdmin);
}
