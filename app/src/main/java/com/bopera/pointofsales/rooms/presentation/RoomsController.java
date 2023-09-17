package com.bopera.pointofsales.rooms.presentation;

import com.bopera.pointofsales.auth.model.RoomRequest;
import com.bopera.pointofsales.model.RoomDetails;
import com.bopera.pointofsales.rooms.service.RoomsPlanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
public class RoomsController {

    private final RoomsPlanService roomsPlanService;

    public RoomsController(RoomsPlanService roomsPlanService) {
        this.roomsPlanService = roomsPlanService;
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

}
