package com.bopera.pointofsales.persistence.repository;

import com.bopera.pointofsales.persistence.entity.PermissionEntity;

import java.util.Optional;

public interface PermissionRepository extends BaseRepository<PermissionEntity, Long> {

    Optional<PermissionEntity> findByPermissionName(String adminAccess);
}
