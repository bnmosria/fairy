package com.bopera.pointofsales.service;

import com.bopera.pointofsales.entity.Room;
import com.bopera.pointofsales.model.HallDetails;
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
    void shouldAddRoomWhenNoRoomsThenSortingIsZero() {
        Room newRoom = getRoom();
        when(roomRepository.findTopByOrderBySortingDesc()).thenReturn(Optional.empty());
        when(roomRepository.save(any())).thenReturn(newRoom);
        HallDetails hallDetails = roomsService.addRoom(
            HallDetails.builder()
                .name("a new room")
                .abbreviation("az")
                .build()
        );

        assertEquals(newRoom.getRoomname(), hallDetails.getName());
        assertEquals(newRoom.getAbbreviation(), hallDetails.getAbbreviation());
        assertEquals(0, hallDetails.getSorting());
        assertEquals(1, hallDetails.getId());
    }

    @Test
    void shouldAddRoomAndReturnCorrectHallDetailsWithSortingIncreased() {
        Room newRoom = getRoom();
        when(roomRepository.findTopByOrderBySortingDesc()).thenReturn(Optional.of(newRoom));
        when(roomRepository.save(any())).thenReturn(newRoom);
        HallDetails hallDetails = roomsService.addRoom(
            HallDetails.builder()
                .name("a new room")
                .abbreviation("az")
                .build()
        );

        assertEquals(newRoom.getRoomname(), hallDetails.getName());
        assertEquals(newRoom.getAbbreviation(), hallDetails.getAbbreviation());
        assertEquals(6, hallDetails.getSorting());
        assertEquals(1, hallDetails.getId());
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
