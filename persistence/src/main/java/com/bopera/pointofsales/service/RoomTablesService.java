package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.Room;
import com.bopera.pointofsales.entity.RoomTable;
import com.bopera.pointofsales.model.HallTableDetails;
import com.bopera.pointofsales.repository.RoomRepository;
import com.bopera.pointofsales.repository.RoomTablesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomTablesService {
    private final RoomTablesRepository roomTablesRepository;
    private final RoomRepository roomRepository;

    public RoomTablesService(RoomTablesRepository roomTablesRepository, RoomRepository roomRepository) {
        this.roomTablesRepository = roomTablesRepository;
        this.roomRepository = roomRepository;
    }

    public List<HallTableDetails> getAllByRoomId(Integer roomId) {
        return roomTablesRepository.findByRoomId(roomId)
            .stream()
            .map(roomTable -> HallTableDetails.builder()
                .id(roomTable.getId())
                .roomId(roomTable.getRoom().getId())
                .name(roomTable.getName())
                .title(roomTable.getTableno())
                .sorting(roomTable.getSorting())
                .build()
            ).collect(Collectors.toList());
    }

    public HallTableDetails saveRoomTable(HallTableDetails hallTable) {
        return roomRepository.findById(hallTable.getRoomId())
            .map(room -> mapToRoomTable(room, hallTable))
            .map(roomTablesRepository::save)
            .map(HallTableDetails::mapFromRoomTable)
            .orElseThrow();
    }


    public HallTableDetails updateRoomTable(HallTableDetails hallTableDetails) {
        return roomTablesRepository.findById(hallTableDetails.getId()).map(
            roomTable -> {
                roomTable.setTableno(hallTableDetails.getTitle());
                roomTable.setName(hallTableDetails.getName());
                roomTable.setCode(hallTableDetails.getCode());
                roomTable.setActive(hallTableDetails.getActive());
                roomTable.setSorting(hallTableDetails.getSorting());

                roomTablesRepository.save(roomTable);

                return hallTableDetails;
            }
        ).orElseThrow();
    }

    public void removeHallTable(int id) {
        this.roomTablesRepository.deleteById(id);
    }

    private RoomTable mapToRoomTable(Room room, HallTableDetails tableDetails) {
        RoomTable roomTable = new RoomTable();

        roomTable.setRoom(room);
        roomTable.setName(tableDetails.getName());
        roomTable.setTableno(tableDetails.getTitle());
        roomTable.setSorting(tableDetails.getSorting());
        roomTable.setActive(tableDetails.getActive());
        roomTable.setCode(tableDetails.getCode());

        return roomTable;
    }

}
