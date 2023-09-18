package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.RoomTables;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTablesRepository extends BaseRepository<RoomTables, Integer> {

    @Query(value = "SELECT * FROM osp_resttables t WHERE t.roomid = :roomId", nativeQuery = true)
    List<RoomTables> findByRoomId(Integer roomId);
}
