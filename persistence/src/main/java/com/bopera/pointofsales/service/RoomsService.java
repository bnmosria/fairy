package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.Room;
import com.bopera.pointofsales.entity.RoomTable;
import com.bopera.pointofsales.model.RoomDetails;
import com.bopera.pointofsales.model.RoomTableDetails;
import com.bopera.pointofsales.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomsService {
    private final RoomRepository roomRepository;

    public RoomsService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomDetails addRoom(Room room) {
        this.roomRepository.findTopByOrderBySortingDesc()
                .ifPresentOrElse(
                    top -> room.setSorting(top.getSorting() + 1),
                    () -> room.setSorting(0)
                );

        this.roomRepository.save(room);

        return buildRoomDetails(room);
    }

    public List<RoomDetails> retrieveAllRooms() {
        return this.roomRepository.findAllByOrderBySorting()
                .stream().map(this::buildRoomDetails)
                .collect(Collectors.toList());
    }

    private RoomDetails buildRoomDetails(Room room) {
        return RoomDetails.builder()
                .roomTableDetails(
                    room.getRoomTables().stream()
                            .map(this::buildRoomTableDetails)
                            .collect(Collectors.toList())
                )
                .id(room.getId())
                .name(room.getRoomname())
                .abbreviation(room.getAbbreviation())
                .sorting(room.getSorting())
                .build();
    }

    private RoomTableDetails buildRoomTableDetails(RoomTable roomTable) {
        return RoomTableDetails.builder()
                .sorting(roomTable.getSorting())
                .title(roomTable.getTableno())
                .name(roomTable.getName())
                .id(roomTable.getId())
                .active(roomTable.getActive())
                .build();
    }
}
