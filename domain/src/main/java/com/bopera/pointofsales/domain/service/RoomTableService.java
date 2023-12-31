package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.HallTableDetails;
import com.bopera.pointofsales.persistence.repository.RoomTablesRepository;
import com.bopera.pointofsales.persistence.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomTableService {
    private final RoomTablesRepository roomTablesRepository;
    private final RoomRepository roomRepository;

    public RoomTableService(RoomTablesRepository roomTablesRepository, RoomRepository roomRepository) {
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
