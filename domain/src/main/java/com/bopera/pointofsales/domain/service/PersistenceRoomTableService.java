package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.exception.EntityNotFoundException;
import com.bopera.pointofsales.domain.model.RoomTable;
import com.bopera.pointofsales.persistence.entity.RoomEntity;
import com.bopera.pointofsales.persistence.entity.RoomTableEntity;
import com.bopera.pointofsales.persistence.repository.RoomRepository;
import com.bopera.pointofsales.persistence.repository.RoomTablesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersistenceRoomTableService {
    private final RoomTablesRepository roomTablesRepository;
    private final RoomRepository roomRepository;

    public PersistenceRoomTableService(
        RoomTablesRepository roomTablesRepository,
        RoomRepository roomRepository) {

        this.roomTablesRepository = roomTablesRepository;
        this.roomRepository = roomRepository;
    }

    public List<RoomTable> getAllByRoomId(Long roomId) {
        return roomTablesRepository.findByRoomId(roomId)
            .stream()
            .map(roomTable -> RoomTable.builder()
                .id(roomTable.getId())
                .build()
            ).collect(Collectors.toList());
    }

    public RoomTable getRoomTableById(Long roomTableId) {
        return roomTablesRepository.findById(roomTableId)
            .map(RoomTable::mapFromRoomTableEntity)
            .orElseThrow(() -> new EntityNotFoundException("Room table not found with ID: " + roomTableId));
    }

    @Transactional
    public RoomEntity saveNewRoomTable(RoomTable roomTable, Long roomId) {
        return roomRepository.findById(roomId)
            .map(roomEntity -> {
                RoomTableEntity roomTableEntity = RoomTable.mapToRoomTableEntity(roomTable);
                roomTableEntity.setRoom(roomEntity);

                roomTablesRepository.save(roomTableEntity);
                roomEntity.getTables().add(roomTableEntity);

                roomRepository.save(roomEntity);

                return roomEntity;
            })
            .orElseThrow(() -> new EntityNotFoundException("Room not found with ID: " + roomId));
    }

    @Transactional
    public RoomEntity updateRoomTable(RoomTable roomTable) {
        RoomTableEntity roomTableEntity = roomTablesRepository.findById(roomTable.getId())
            .orElseThrow(() -> new EntityNotFoundException("Room table not found with ID: " + roomTable.getId()));

        roomTableEntity.setName(roomTable.getName());
        roomTableEntity.setTableNumber(roomTable.getTableNumber());
        roomTableEntity.setActive(roomTable.getActive());

        return roomRepository.save(roomTableEntity.getRoom());
    }

    @Transactional
    public void deleteRoomTable(Long roomTableId) {
        roomTablesRepository.findById(roomTableId)
            .map(roomTableEntity -> {
                RoomEntity roomEntity = roomTableEntity.getRoom();
                roomEntity.getTables().remove(roomTableEntity);

                roomTablesRepository.delete(roomTableEntity);

                return roomTableEntity;
            })
            .orElseThrow(() -> new EntityNotFoundException("Room table not found with ID: " + roomTableId));

    }
}
