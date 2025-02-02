package com.bopera.pointofsales.api.room.controller;

import com.bopera.pointofsales.domain.interfaces.RoomServiceInterface;
import com.bopera.pointofsales.domain.model.Room;
import com.bopera.pointofsales.api.room.model.request.EditRoomRequest;
import com.bopera.pointofsales.api.room.model.request.SaveRoomRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/rooms")
public class RoomsController {

    private final RoomServiceInterface roomService;

    public RoomsController(RoomServiceInterface roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<List<Room>> getAll() {
        return ResponseEntity.ok(
            this.roomService.retrieveAllRooms()
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addNewRoom(@Valid @RequestBody SaveRoomRequest hallRequest) {

        Room roomDetails = this.roomService.addRoom(
            Room.builder()
                .name(hallRequest.getName())
                .sorting(hallRequest.getSorting())
                .build()
        );

        return ResponseEntity.ok(roomDetails);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Room> updateRoom(@Valid @RequestBody EditRoomRequest hallRequest) {
        Room roomDetails = this.roomService.updateRoom(
            Room.builder()
                .id(hallRequest.getId())
                .name(hallRequest.getName())
                .sorting(hallRequest.getSorting())
                .build()
        );

        return ResponseEntity.ok(roomDetails);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeRoom(@PathVariable("id") long id) {
        this.roomService.removeRoom(id);
        return ResponseEntity.noContent().build();
    }

}
