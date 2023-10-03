package com.bopera.pointofsales.service;

import com.bopera.pointofsales.model.HallTableDetails;
import com.bopera.pointofsales.repository.RoomTablesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
