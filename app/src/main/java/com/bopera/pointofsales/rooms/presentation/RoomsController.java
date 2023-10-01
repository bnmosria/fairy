package com.bopera.pointofsales.rooms.presentation;

import com.bopera.pointofsales.auth.model.RoomRequest;
import com.bopera.pointofsales.model.RoomDetails;
import com.bopera.pointofsales.rooms.service.RoomsPlanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomsController {

    private final RoomsPlanService roomsPlanService;

    public RoomsController(RoomsPlanService roomsPlanService) {
        this.roomsPlanService = roomsPlanService;
    }

    @GetMapping
    public ResponseEntity<List<RoomDetails>> getAll() {
        return ResponseEntity.ok(
                this.roomsPlanService.retrieveAllRooms()
        );
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<RoomDetails> addNewRoom(@Valid @RequestBody RoomRequest roomRequest) throws Exception {
        RoomDetails roomDetails = this.roomsPlanService.addRoom(roomRequest);

        if (0 == roomDetails.getId()) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(roomDetails);
    }


    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> removeRoom(@PathVariable int id) {
        this.roomsPlanService.removeRoom(id);
        return ResponseEntity.noContent().build();
    }

}
