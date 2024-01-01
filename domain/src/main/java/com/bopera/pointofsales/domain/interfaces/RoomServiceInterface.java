package com.bopera.pointofsales.domain.interfaces;

import com.bopera.pointofsales.domain.model.Room;

import java.util.List;

public interface RoomServiceInterface {
    List<Room> retrieveAllRooms();

    Room addRoom(Room roomDetails);

    void removeRoom(long roomId);

    Room updateRoom(Room roomDetails);
}
