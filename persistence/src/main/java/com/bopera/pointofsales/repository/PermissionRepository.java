package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.Permission;

import java.util.Optional;

public interface PermissionRepository extends BaseRepository<Permission, Long> {

    Optional<Permission> findByPermissionName(String adminAccess);
}
