package com.bopera.pointofsales.halls.presentation;

import com.bopera.pointofsales.auth.model.EditHallRequest;
import com.bopera.pointofsales.auth.model.SaveHallRequest;
import com.bopera.pointofsales.auth.model.SaveHallTableRequest;
import com.bopera.pointofsales.model.HallDetails;
import com.bopera.pointofsales.halls.service.HallsPlanService;
import com.bopera.pointofsales.model.HallTableDetails;
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
    public ResponseEntity<List<HallDetails>> getAll() {
        return ResponseEntity.ok(
            this.hallsPlanService.retrieveAllRooms()
        );
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<HallDetails> addNewHall(@Valid @RequestBody SaveHallRequest hallRequest) {
        HallDetails hallDetails = this.hallsPlanService.addHall(hallRequest);

        if (0 == hallDetails.getId()) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(hallDetails);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<HallDetails> editHall(@Valid @RequestBody EditHallRequest hallRequest) {
        HallDetails hallDetails = this.hallsPlanService.editHall(hallRequest);

        if (0 == hallDetails.getId()) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(hallDetails);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> removeHall(@PathVariable int id) {
        this.hallsPlanService.removeRoom(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/table/add")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<HallTableDetails> addNewHallTable(@Valid @RequestBody SaveHallTableRequest saveHallTableRequest) {
        HallTableDetails hallDetails = this.hallsPlanService.addHallTable(saveHallTableRequest);

        if (0 == hallDetails.getId()) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(hallDetails);
    }
}
