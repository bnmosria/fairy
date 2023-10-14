package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.RoomTable;
import com.bopera.pointofsales.model.HallTableDetails;
import com.bopera.pointofsales.repository.RoomTablesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomTablesService {
    private final RoomTablesRepository roomTablesRepository;

    public RoomTablesService(RoomTablesRepository roomTablesRepository) {
        this.roomTablesRepository = roomTablesRepository;
    }

    public List<HallTableDetails> getAllByRoomId(Integer roomId) {
        return roomTablesRepository.findByRoomId(roomId)
                .stream()
                .map(roomTable -> HallTableDetails.builder()
                    .id(roomTable.getId())
                    .room(roomTable.getRoom())
                    .name(roomTable.getName())
                    .title(roomTable.getTableno())
                    .sorting(roomTable.getSorting())
                    .build()
                ).collect(Collectors.toList());
    }

    public RoomTable saveRoomTable(HallTableDetails hallTable) {
        return Optional.ofNullable(hallTable)
                .map(this::mapToRoomTable)
                .map(roomTablesRepository::save)
                .orElseThrow();
    }

    private RoomTable mapToRoomTable(HallTableDetails tableDetails) {
        RoomTable roomTable = new RoomTable();

        roomTable.setRoom(tableDetails.getRoom());
        roomTable.setName(tableDetails.getName());
        roomTable.setTableno(tableDetails.getTitle());
        roomTable.setSorting(tableDetails.getSorting());
        roomTable.setActive(tableDetails.getActive());
        roomTable.setCode(tableDetails.getCode());

        return roomTable;
    }
}
