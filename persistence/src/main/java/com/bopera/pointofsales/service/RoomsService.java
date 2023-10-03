package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.Room;
import com.bopera.pointofsales.entity.RoomTable;
import com.bopera.pointofsales.model.HallDetails;
import com.bopera.pointofsales.model.HallTableDetails;
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

    public List<HallDetails> retrieveAllRooms() {
        return this.roomRepository.findAllByOrderBySorting()
                .stream().map(this::buildHallDetails)
                .collect(Collectors.toList());
    }

    public HallDetails addRoom(Room room) {
        this.roomRepository.findTopByOrderBySortingDesc()
                .ifPresentOrElse(
                        top -> room.setSorting(top.getSorting() + 1),
                        () -> room.setSorting(0)
                );

        this.roomRepository.save(room);

        return buildHallDetails(room);
    }

    public void removeRoom(int roomId) {
        roomRepository.deleteById(roomId);
    }

    private HallDetails buildHallDetails(Room room) {
        return HallDetails.builder()
                .hallTableDetails(
                    room.getRoomTables().stream()
                            .map(this::buildHallTableDetails)
                            .collect(Collectors.toList())
                )
                .id(room.getId())
                .name(room.getRoomname())
                .abbreviation(room.getAbbreviation())
                .sorting(room.getSorting())
                .build();
    }

    private HallTableDetails buildHallTableDetails(RoomTable roomTable) {
        return HallTableDetails.builder()
                .sorting(roomTable.getSorting())
                .title(roomTable.getTableno())
                .name(roomTable.getName())
                .id(roomTable.getId())
                .active(roomTable.getActive())
                .build();
    }

}
