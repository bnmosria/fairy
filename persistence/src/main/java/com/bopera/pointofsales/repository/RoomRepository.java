package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.Room;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends BaseRepository<Room, Long> {
    Optional<Room> findTopByOrderBySortingDesc();

    List<Room> findAllByOrderBySortingDesc();
}
