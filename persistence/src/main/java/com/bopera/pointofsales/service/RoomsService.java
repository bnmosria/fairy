package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.Room;
import com.bopera.pointofsales.model.RoomDetails;
import com.bopera.pointofsales.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomsService {
    private final RoomRepository roomRepository;

    public RoomsService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomDetails addRoom(Room room) {

        Room currentSortingValue = this.roomRepository.findTopByOrderBySortingDesc()
                .orElseGet(() -> {
                    room.setSorting(0);
                    return room;
                });

        room.setSorting(currentSortingValue.getSorting() + 1);
        this.roomRepository.save(room);

        return RoomDetails.builder()
                .id(room.getId())
                .name(room.getRoomname())
                .abbreviation(room.getAbbreviation())
                .sorting(room.getSorting())
                .build();
    }
}
