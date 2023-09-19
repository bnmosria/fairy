package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.Room;
import com.bopera.pointofsales.model.RoomDetails;
import com.bopera.pointofsales.repository.RoomRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomsServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomsService roomsService;

    @BeforeEach
    void setUp() {
        roomsService = new RoomsService(roomRepository);
    }

    @Test
    void shouldAddRoomWhenRoomExistsThenSortingIncreases() {
        Room existingRoom = getRoom();
        existingRoom.setSorting(5);
        when(roomRepository.findTopByOrderBySortingDesc()).thenReturn(Optional.of(existingRoom));
        Room newRoom = getRoom();

        roomsService.addRoom(newRoom);
        verify(roomRepository, times(1)).save(newRoom);
        assertEquals(6, newRoom.getSorting());
    }

    @Test
    void shouldAddRoomWhenNoRoomsThenSortingIsZero() {
        when(roomRepository.findTopByOrderBySortingDesc()).thenReturn(Optional.empty());
        Room newRoom = getRoom();
        roomsService.addRoom(newRoom);
        verify(roomRepository, times(1)).save(newRoom);
        assertEquals(0, newRoom.getSorting());
    }


    @Test
    void shouldAddRoomWhenRoomAddedThenSortingValueIncrementedAndRoomSaved() {
        Room newRoom = getRoom();

        when(roomRepository.findTopByOrderBySortingDesc()).thenReturn(Optional.of(newRoom));
        roomsService.addRoom(newRoom);

        verify(roomRepository, times(1)).save(newRoom);
        assertEquals(6, newRoom.getSorting());
    }

    @Test
    void shouldAddRoomWhenRoomAddedThenCorrectRoomDetailsReturned() {
        Room newRoom = getRoom();
        when(roomRepository.findTopByOrderBySortingDesc()).thenReturn(Optional.of(newRoom));
        RoomDetails roomDetails = roomsService.addRoom(newRoom);

        assertEquals(newRoom.getRoomname(), roomDetails.getName());
        assertEquals(newRoom.getAbbreviation(), roomDetails.getAbbreviation());
        assertEquals(6, roomDetails.getSorting());
    }

    @Test
    void shouldReturnTheCorrectRoomDetailsWhenNewRoomIsCreated() {
        Room newRoom = getRoom();

        RoomDetails roomDetails = roomsService.addRoom(newRoom);

        assertEquals(roomDetails.getName(), newRoom.getRoomname());
    }

    @Test
    void shouldSetTheCorrectSortingForRoomWhenNewRoomIsCreated() {
        Room newRoom = getRoom();
        newRoom.setSorting(5);

        doReturn(Optional.of(newRoom)).when(roomRepository).findTopByOrderBySortingDesc();
        RoomDetails roomDetails = roomsService.addRoom(newRoom);

        assertEquals(6, roomDetails.getSorting());
    }

    @NotNull
    private static Room getRoom() {
        Room newRoom = new Room();
        newRoom.setRoomname("a new room");
        newRoom.setAbbreviation("az");
        newRoom.setSorting(5);
        newRoom.setId(1);
        newRoom.setRoomTables(List.of());
        return newRoom;
    }

}
