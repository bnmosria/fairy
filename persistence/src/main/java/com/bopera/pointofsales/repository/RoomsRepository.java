package com.bopera.pointofsales.repository;

import com.bopera.pointofsales.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoomsRepository extends BaseRepository<Room, Long>{

}
