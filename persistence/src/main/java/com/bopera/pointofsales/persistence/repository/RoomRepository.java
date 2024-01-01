package com.bopera.pointofsales.persistence.repository;

import com.bopera.pointofsales.persistence.entity.RoomEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends BaseRepository<RoomEntity, Long> {
    Optional<RoomEntity> findTopByOrderBySortingDesc();

    List<RoomEntity> findAllByOrderBySortingDesc();
}
