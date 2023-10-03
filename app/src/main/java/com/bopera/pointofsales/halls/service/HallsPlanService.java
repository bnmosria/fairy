package com.bopera.pointofsales.halls.service;

import com.bopera.pointofsales.auth.model.EditHallRequest;
import com.bopera.pointofsales.auth.model.SaveHallRequest;
import com.bopera.pointofsales.entity.Room;
import com.bopera.pointofsales.model.HallDetails;
import com.bopera.pointofsales.service.RoomsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HallsPlanService {
    private final RoomsService roomsService;

    public HallsPlanService(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    public HallDetails addHall(SaveHallRequest roomRequest) {
        Room newRoom = new Room();

        newRoom.setRoomname(roomRequest.getName());
        newRoom.setAbbreviation(roomRequest.getAbbreviation());

        return this.roomsService.addRoom(newRoom);
    }

    public List<HallDetails> retrieveAllRooms() {
        return this.roomsService.retrieveAllRooms();
    }

    public void removeRoom(int roomId) {
        log.info("The room with id {} will be removed", roomId);
        this.roomsService.removeRoom(roomId);
    }

    public HallDetails editHall(EditHallRequest hallRequest) {
        log.info("The room with id {} will be edited", hallRequest.getId());
        return this.roomsService.editHall(
            HallDetails.builder()
                .abbreviation(hallRequest.getAbbreviation())
                .id(hallRequest.getId())
                .name(hallRequest.getName())
                .sorting(hallRequest.getSorting())
                .build()
        );
    }
}
