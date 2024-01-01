package com.bopera.pointofsales.domain.service;

import com.bopera.pointofsales.domain.model.Room;
import com.bopera.pointofsales.persistence.entity.RoomEntity;
import com.bopera.pointofsales.persistence.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersistenceRoomsServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private PersistenceRoomService roomsService;

    @BeforeEach
    void setUp() {
        roomsService = new PersistenceRoomService(roomRepository);
    }

    @Test
    void shouldAddRoomWhenNoRoomsThenSortingIsZero() {
        RoomEntity newRoom = new RoomEntity();
        newRoom.setId(1L);
        newRoom.setRoomName("a new room");

        when(roomRepository.findTopByOrderBySortingDesc()).thenReturn(Optional.empty());
        when(roomRepository.save(any())).thenReturn(newRoom);
        Room hallDetails = roomsService.addRoom(
            Room.builder()
                .name("a new room")
                .build()
        );

        assertEquals(newRoom.getRoomName(), hallDetails.getName());
        assertEquals(0, hallDetails.getSorting());
        assertEquals(1, hallDetails.getId());
    }
}
