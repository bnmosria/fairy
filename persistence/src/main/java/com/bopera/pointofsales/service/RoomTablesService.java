package com.bopera.pointofsales.service;

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
                .build()
            ).collect(Collectors.toList());
    }

}
