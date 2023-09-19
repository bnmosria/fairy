package com.bopera.pointofsales.rooms.service;

import com.bopera.pointofsales.auth.model.RoomRequest;
import com.bopera.pointofsales.entity.Room;
import com.bopera.pointofsales.model.RoomDetails;
import com.bopera.pointofsales.service.RoomsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoomsPlanService {
    private final RoomsService roomsService;

    public RoomsPlanService(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    public RoomDetails addRoom(RoomRequest roomRequest) {
        Room newRoom = new Room();

        newRoom.setRoomname(roomRequest.getName());
        newRoom.setAbbreviation(roomRequest.getAbbreviation());

        return this.roomsService.addRoom(newRoom);
    }

    public List<RoomDetails> retrieveAllRooms() {
        return this.roomsService.retrieveAllRooms();
    }
}
