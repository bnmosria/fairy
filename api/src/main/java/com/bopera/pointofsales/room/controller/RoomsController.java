package com.bopera.pointofsales.room.controller;

import com.bopera.pointofsales.domain.interfaces.RoomServiceInterface;
import com.bopera.pointofsales.room.model.request.EditRoomRequest;
import com.bopera.pointofsales.room.model.request.SaveRoomRequest;
import com.bopera.pointofsales.domain.model.Room;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Room> addNewHall(@Valid @RequestBody SaveRoomRequest hallRequest) {

        Room roomDetails = this.roomService.addRoom(
            Room.builder()
                .name(hallRequest.getName())
                .sorting(hallRequest.getSorting())
                .build()
        );

        if (0 == roomDetails.getId()) {
            return ResponseEntity.internalServerError().build();
        }

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
    public ResponseEntity<Void> removeRoom(@PathVariable long id) {
        this.roomService.removeRoom(id);
        return ResponseEntity.noContent().build();
    }

}
