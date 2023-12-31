package com.bopera.pointofsales.halls.controller;

import com.bopera.pointofsales.halls.model.request.EditHallRequest;
import com.bopera.pointofsales.halls.model.request.SaveHallRequest;
import com.bopera.pointofsales.domain.model.HallDetails;
import com.bopera.pointofsales.halls.service.HallsPlanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class HallsController {

    private final HallsPlanService hallsPlanService;

    public HallsController(HallsPlanService hallsPlanService) {
        this.hallsPlanService = hallsPlanService;
    }

    @GetMapping
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<List<HallDetails>> getAll() {
        return ResponseEntity.ok(
            this.hallsPlanService.retrieveAllRooms()
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HallDetails> addNewHall(@Valid @RequestBody SaveHallRequest hallRequest) {
        HallDetails hallDetails = this.hallsPlanService.addHall(hallRequest);

        if (0 == hallDetails.getId()) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(hallDetails);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HallDetails> editHall(@Valid @RequestBody EditHallRequest hallRequest) {
        HallDetails hallDetails = this.hallsPlanService.editHall(hallRequest);

        if (0 == hallDetails.getId()) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(hallDetails);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeHall(@PathVariable int id) {
        this.hallsPlanService.removeRoom(id);
        return ResponseEntity.noContent().build();
    }

}
