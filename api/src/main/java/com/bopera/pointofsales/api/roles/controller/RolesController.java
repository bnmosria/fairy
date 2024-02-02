package com.bopera.pointofsales.api.roles.controller;

import com.bopera.pointofsales.api.roles.model.request.EditRoleRequest;
import com.bopera.pointofsales.api.roles.model.request.RoleRequest;
import com.bopera.pointofsales.api.roles.model.response.RoleResponse;
import com.bopera.pointofsales.domain.interfaces.RoleService;
import com.bopera.pointofsales.domain.service.RolePersistence;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final RoleService roleService;

    public RolesController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RoleResponse>> getAll() {
        return ResponseEntity.ok(List.of(new RoleResponse()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addNewRoom(@Valid @RequestBody RoleRequest roleRequest) {
        return ResponseEntity.ok(new RoleResponse());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRoom(@Valid @RequestBody EditRoleRequest roleRequest) {
        return ResponseEntity.ok(new RoleResponse());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeRoom(@PathVariable("id") long id) {
        return ResponseEntity.noContent().build();
    }

}
