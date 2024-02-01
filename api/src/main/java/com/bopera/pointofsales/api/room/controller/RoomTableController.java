package com.bopera.pointofsales.api.room.controller;

import com.bopera.pointofsales.domain.interfaces.RoomTablesServiceInterface;
import com.bopera.pointofsales.domain.model.RoomTable;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-tables")
public class RoomTableController {
    private final RoomTablesServiceInterface roomTableService;

    public RoomTableController(RoomTablesServiceInterface roomTableService) {
        this.roomTableService = roomTableService;
    }

    @GetMapping("/{roomId}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<List<RoomTable>> getAllRoomTablesByRoomId(@PathVariable Long roomId) {
        return ResponseEntity.ok(
            roomTableService.getAllByRoomId(roomId)
        );
    }

    @GetMapping("/{roomTableId}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<RoomTable> getRoomTableById(@PathVariable Long roomTableId) {
        return ResponseEntity.ok(roomTableService.getRoomTableById(roomTableId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomTable> saveNewRoomTable(@Valid @RequestBody RoomTable roomTable) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            roomTableService.saveNewRoomTable(roomTable)
        );
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomTable> updateRoomTable(@Valid @RequestBody RoomTable roomTable) {
        return ResponseEntity.ok(
            roomTableService.updateRoomTable(roomTable)
        );
    }

    @DeleteMapping("/{roomTableId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRoomTable(@PathVariable Long roomTableId) {
        roomTableService.deleteRoomTable(roomTableId);
        return ResponseEntity.noContent().build();
    }
}
