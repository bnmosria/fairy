package com.bopera.pointofsales.api.roles.controller;

import com.bopera.pointofsales.api.roles.model.request.EditRoleRequest;
import com.bopera.pointofsales.api.roles.model.request.RoleRequest;
import com.bopera.pointofsales.api.roles.model.response.RoleResponse;
import com.bopera.pointofsales.domain.interfaces.RoleService;
import com.bopera.pointofsales.domain.model.Role;
import com.bopera.pointofsales.domain.service.RolePersistence;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;


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
        return ResponseEntity.ok(List.of(RoleResponse.builder().build()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<RoleResponse>> addRole(
        @Valid @RequestBody Mono<RoleRequest> roleRequestMono) {

        return roleRequestMono
            .map(roleRequest -> this.roleService.addRole(Role.builder().build()))
            .map(role -> ResponseEntity.ok(
                    RoleResponse.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .build()
                )
            );
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRole(@Valid @RequestBody EditRoleRequest roleRequest) {
        return ResponseEntity.ok(RoleResponse.builder().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeRole(@PathVariable("id") long id) {
        return ResponseEntity.noContent().build();
    }

}
