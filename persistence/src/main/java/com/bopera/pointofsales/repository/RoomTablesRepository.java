package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.RoomTable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTablesRepository extends BaseRepository<RoomTable, Integer> {

    @Query(value = "SELECT * FROM tables t WHERE t.room_id = :roomId", nativeQuery = true)
    List<RoomTable> findByRoomId(Integer roomId);
}
