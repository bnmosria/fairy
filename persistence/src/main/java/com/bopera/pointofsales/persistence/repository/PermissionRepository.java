package com.bopera.pointofsales.persistence.repository;

import com.bopera.pointofsales.persistence.entity.Permission;

import java.util.Optional;

public interface PermissionRepository extends BaseRepository<Permission, Long> {

    Optional<Permission> findByPermissionName(String adminAccess);
}
