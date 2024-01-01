package com.bopera.pointofsales.persistence.repository;

import com.bopera.pointofsales.persistence.entity.RoleEntity;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleName(String roleAdmin);
}
