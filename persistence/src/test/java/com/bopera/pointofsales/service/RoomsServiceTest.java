package com.bopera.pointofsales.service;

import com.bopera.pointofsales.persistence.entity.Room;
import com.bopera.pointofsales.persistence.model.HallDetails;
import com.bopera.pointofsales.persistence.repository.RoomRepository;
import com.bopera.pointofsales.persistence.service.RoomsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Room newRoom = new Room();
        newRoom.setId(1L);
        newRoom.setRoomName("a new room");

        when(roomRepository.findTopByOrderBySortingDesc()).thenReturn(Optional.empty());
        when(roomRepository.save(any())).thenReturn(newRoom);
        HallDetails hallDetails = roomsService.addRoom(
            HallDetails.builder()
                .name("a new room")
                .build()
        );

        assertEquals(newRoom.getRoomName(), hallDetails.getName());
        assertEquals(0, hallDetails.getSorting());
        assertEquals(1, hallDetails.getId());
    }
}
