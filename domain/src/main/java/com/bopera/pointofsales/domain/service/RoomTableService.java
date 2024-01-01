package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.RoomTable;
import com.bopera.pointofsales.persistence.repository.RoomTablesRepository;
import com.bopera.pointofsales.persistence.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomTableService {
    private final RoomTablesRepository roomTablesRepository;

    public RoomTableService(RoomTablesRepository roomTablesRepository, RoomRepository roomRepository) {
        this.roomTablesRepository = roomTablesRepository;
    }

    public List<RoomTable> getAllByRoomId(Integer roomId) {
        return roomTablesRepository.findByRoomId(roomId)
            .stream()
            .map(roomTable -> RoomTable.builder()
                .id(roomTable.getId())
                .build()
            ).collect(Collectors.toList());
    }

}
