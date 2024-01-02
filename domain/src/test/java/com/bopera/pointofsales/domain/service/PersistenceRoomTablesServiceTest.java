package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.exception.EntityNotFoundException;
import com.bopera.pointofsales.domain.model.RoomTable;
import com.bopera.pointofsales.persistence.entity.RoomEntity;
import com.bopera.pointofsales.persistence.entity.RoomTableEntity;
import com.bopera.pointofsales.persistence.repository.RoomRepository;
import com.bopera.pointofsales.persistence.repository.RoomTablesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PersistenceRoomTablesServiceTest {

    private PersistenceRoomTableService roomTableService;

    @Mock
    private RoomTablesRepository roomTablesRepository;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        roomTableService = new PersistenceRoomTableService(roomTablesRepository, roomRepository);
    }

    @Test
    void ShouldGetAllByRoomId_WhenValidRoomId() {
        Long roomId = 1L;
        RoomTableEntity roomTableEntity1 = new RoomTableEntity();
        RoomTableEntity roomTableEntity2 = new RoomTableEntity();
        roomTableEntity1.setId(1L);
        roomTableEntity2.setId(2L);

        List<RoomTableEntity> roomTableEntities = List.of(
            roomTableEntity1, roomTableEntity2
        );

        when(roomTablesRepository.findByRoomId(roomId)).thenReturn(roomTableEntities);

        List<RoomTable> roomTables = roomTableService.getAllByRoomId(roomId);

        assertEquals(2, roomTables.size());
        assertEquals(1L, roomTables.get(0).getId());
        assertEquals(2L, roomTables.get(1).getId());
    }

    @Test
    void ShouldGetRoomTableById_WhenValidRoomTableId() {
        Long roomTableId = 1L;
        RoomTableEntity roomTableEntity = new RoomTableEntity();
        roomTableEntity.setId(1L);

        when(roomTablesRepository.findById(roomTableId)).thenReturn(Optional.of(roomTableEntity));

        RoomTable roomTable = roomTableService.getRoomTableById(roomTableId);

        assertNotNull(roomTable);
        assertEquals(roomTableId, roomTable.getId());
    }

    @Test
    void ShouldThrowEntityNotFoundException_WhenInvalidRoomTableId() {
        Long roomTableId = 1L;
        when(roomTablesRepository.findById(roomTableId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roomTableService.getRoomTableById(roomTableId));
    }

    @Test
    void ShouldSaveNewRoomTable_WhenValidRoomId() {
        Long roomId = 1L;
        RoomTable roomTable = RoomTable.builder().build();
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(roomId);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(roomEntity));

        RoomEntity savedRoomEntity = roomTableService.saveNewRoomTable(roomTable, roomId);
        assertNotNull(savedRoomEntity);
        assertEquals(roomEntity, savedRoomEntity);
        verify(roomTablesRepository, times(1)).save(any(RoomTableEntity.class));
        verify(roomRepository, times(1)).save(any(RoomEntity.class));
    }


    @Test
    void ShouldUpdateRoomTable_WhenValidRoomTableId() {
        Long roomTableId = 1L;
        RoomTable roomTable = RoomTable.builder().id(roomTableId).build();
        RoomTableEntity roomTableEntity = new RoomTableEntity();
        roomTableEntity.setId(roomTableId);

        when(roomTablesRepository.findById(roomTableId)).thenReturn(Optional.of(roomTableEntity));
        when(roomRepository.save(any())).thenReturn(new RoomEntity());

        RoomEntity updatedRoomEntity = roomTableService.updateRoomTable(roomTable);

        assertNotNull(updatedRoomEntity);
        verify(roomTablesRepository, times(1)).findById(roomTableId);
        verify(roomRepository, times(1)).save(any());
    }

    @Test
    void ShouldThrowEntityNotFoundExceptionOnUpdate_WhenInvalidRoomTableId() {

        RoomTable roomTable = RoomTable.builder().build();
        when(roomTablesRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roomTableService.updateRoomTable(roomTable));
    }

    @Test
    void ShouldDeleteRoomTable_WhenValidRoomTableId() {
        Long roomTableId = 1L;
        RoomTableEntity roomTableEntity = new RoomTableEntity();
        roomTableEntity.setId(roomTableId);

        RoomEntity roomEntity = new RoomEntity();
        roomEntity.getTables().add(roomTableEntity);

        spy(roomTableEntity).setRoom(roomEntity);

        final RoomTableEntity spyRoomTableEntity = spy(roomTableEntity);
        doReturn(roomEntity).when(spyRoomTableEntity).getRoom();

        when(roomTablesRepository.findById(roomTableId)).thenReturn(Optional.of(spyRoomTableEntity));

        roomTableService.deleteRoomTable(roomTableId);

        verify(roomTablesRepository, times(1)).findById(roomTableId);
        verify(roomTablesRepository, times(1)).delete(spyRoomTableEntity);
    }

    @Test
    void ShouldThrowEntityNotFoundExceptionOnDelete_WhenInvalidRoomTableId() {
        Long roomTableId = 1L;
        when(roomTablesRepository.findById(roomTableId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roomTableService.deleteRoomTable(roomTableId));
    }
}
