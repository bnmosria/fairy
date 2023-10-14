package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.RoomTable;
import com.bopera.pointofsales.model.HallTableDetails;
import com.bopera.pointofsales.repository.RoomTablesRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomTablesServiceTest {

    @Mock
    private RoomTablesRepository roomTablesRepository;

    private RoomTablesService roomTablesService;

    @BeforeEach
    public void setUp() {
        roomTablesService = new RoomTablesService(roomTablesRepository);
    }

    @Test
    public void shouldReturnSavedRoomTableWhenHallTableNotNull() {
        RoomTable roomTable = new RoomTable();
        when(roomTablesRepository.save(any(RoomTable.class))).thenReturn(roomTable);

        RoomTable result = roomTablesService.saveRoomTable(HallTableDetails.builder().build());

        assertEquals(roomTable, result);
        verify(roomTablesRepository).save(any(RoomTable.class));
    }

    @Test
    public void shouldThrowExceptionWhenHallTableNull() {
        assertThrows(NoSuchElementException.class, () -> roomTablesService.saveRoomTable(null));
    }

    @Test
    public void shouldGetAllByRoomIdWhenRoomIdIsValid() {
        RoomTable roomTable = getRoomTable();
        when(roomTablesRepository.findByRoomId(1)).thenReturn(Collections.singletonList(roomTable));
        List<HallTableDetails> result = roomTablesService.getAllByRoomId(1);

        assertEquals(1, result.size());
        HallTableDetails details = result.get(0);
        assertEquals(roomTable.getId(), details.getId());
        assertEquals(roomTable.getName(), details.getName());
        assertEquals(roomTable.getTableno(), details.getTitle());
        assertEquals(roomTable.getSorting(), details.getSorting());
    }

    @Test
    public void shouldReturnEmptyListWhenRoomIdDoesNotExist() {
        when(roomTablesRepository.findByRoomId(1)).thenReturn(Collections.emptyList());
        List<HallTableDetails> result = roomTablesService.getAllByRoomId(1);
        assertEquals(0, result.size());
    }

    @Test
    public void shouldThrowsExceptionWhenRepositoryThrowsException() {
        when(roomTablesRepository.findByRoomId(1)).thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> roomTablesService.getAllByRoomId(1));
    }

    @Test
    public void shouldReturnCorrectRoomTableDetailsWhenRoomIdIsValid() {
        RoomTable roomTable = getRoomTable();

        when(roomTablesRepository.findByRoomId(1)).thenReturn(Collections.singletonList(roomTable));
        List<HallTableDetails> result = roomTablesService.getAllByRoomId(1);

        assertEquals(1, result.size());

        HallTableDetails details = result.get(0);

        assertEquals(roomTable.getId(), details.getId());
        assertEquals(roomTable.getName(), details.getName());
        assertEquals(roomTable.getTableno(), details.getTitle());
        assertEquals(roomTable.getSorting(), details.getSorting());
    }

    @NotNull
    private static RoomTable getRoomTable() {
        RoomTable roomTable = new RoomTable();
        roomTable.setId(1);
        roomTable.setName("Table1");
        roomTable.setTableno("T1");
        roomTable.setSorting(1);

        return roomTable;
    }
}
