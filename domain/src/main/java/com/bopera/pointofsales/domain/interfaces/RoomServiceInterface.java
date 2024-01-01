package com.bopera.pointofsales.domain.interfaces;

import com.bopera.pointofsales.domain.model.RoomDetails;

import java.util.List;

public interface RoomServiceInterface {
    List<RoomDetails> retrieveAllRooms();

    RoomDetails addRoom(RoomDetails roomDetails);

    void removeRoom(long roomId);

    RoomDetails updateRoom(RoomDetails roomDetails);
}
