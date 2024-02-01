package com.bopera.pointofsales.api.room.controller;

import com.bopera.pointofsales.domain.interfaces.RoomServiceInterface;
import com.bopera.pointofsales.domain.model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {RoomsController.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EnableWebMvc
class RoomsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomServiceInterface roomService;

    private Room room1;
    private Room room2;

    @BeforeEach
    void setUp() {
        room1 = Room.builder().id(1L).name("Room A").sorting(1).build();
        room2 = Room.builder().id(2L).name("Room B").sorting(2).build();
    }

    @Test
    @WithMockUser
    void ShouldReturnListOfRooms_WhenGetAllIsCalled() throws Exception {
        List<Room> rooms = Arrays.asList(room1, room2);
        Mockito.when(roomService.retrieveAllRooms()).thenReturn(rooms);

        mockMvc.perform(get("/api/rooms"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").value(room1.getId()))
            .andExpect(jsonPath("$[1].id").value(room2.getId()));
    }

    @Test
    @WithMockUser
    void ShouldReturnAddedRoom_WhenCalledWithValidRequest() throws Exception {
        Room addedRoom = Room.builder().id(3L).name("Room C").sorting(3).build();
        Mockito.when(roomService.addRoom(Mockito.any(Room.class))).thenReturn(addedRoom);

        mockMvc.perform(post("/api/rooms")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Room C\",\"sorting\":3}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(addedRoom.getId()))
            .andExpect(jsonPath("$.name").value(addedRoom.getName()));
    }

    @Test
    @WithMockUser
    void ShouldReturnUpdatedRoom_WhenCalledWithValidRequest() throws Exception {

        Room updatedRoom = Room.builder().id(1L).name("Updated Room A").sorting(1).build();
        Mockito.when(roomService.updateRoom(Mockito.any(Room.class))).thenReturn(updatedRoom);

        mockMvc.perform(
                put("/api/rooms")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"id\":1,\"name\":\"Updated Room A\",\"sorting\":1}")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(updatedRoom.getId()))
            .andExpect(jsonPath("$.name").value(updatedRoom.getName()));
    }

    @Test
    @WithMockUser
    void ShouldReturnNoContent_WhenCalledWithValidId() throws Exception {
        Mockito.doNothing().when(roomService).removeRoom(1L);

        mockMvc.perform(
                delete("/api/rooms/{id}", 1L)
                    .param("id", "1")
                    .with(csrf())
            )
            .andExpect(status().isNoContent());
    }
}
