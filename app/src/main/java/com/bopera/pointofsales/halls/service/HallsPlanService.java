package com.bopera.pointofsales.halls.service;

import com.bopera.pointofsales.halls.model.request.EditHallRequest;
import com.bopera.pointofsales.halls.model.request.SaveHallRequest;
import com.bopera.pointofsales.persistence.model.HallDetails;
import com.bopera.pointofsales.persistence.service.RoomTablesService;
import com.bopera.pointofsales.persistence.service.RoomsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HallsPlanService {
    private final RoomsService roomsService;
    private final RoomTablesService roomTablesService;

    public HallsPlanService(RoomsService roomsService, RoomTablesService roomTablesService) {
        this.roomsService = roomsService;
        this.roomTablesService = roomTablesService;
    }

    public HallDetails addHall(SaveHallRequest hallRequest) {
        return this.roomsService.addRoom(
            HallDetails.builder()
                .name(hallRequest.getName())
                .sorting(hallRequest.getSorting())
                .build()
        );
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
        return this.roomsService.updateRoom(
            HallDetails.builder()
                .id(hallRequest.getId())
                .name(hallRequest.getName())
                .sorting(hallRequest.getSorting())
                .build()
        );
    }
}
