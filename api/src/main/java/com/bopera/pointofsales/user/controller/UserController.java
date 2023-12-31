package com.bopera.pointofsales.user.controller;

import com.bopera.pointofsales.domain.interfaces.UserServiceInterface;
import com.bopera.pointofsales.user.model.request.CreateUser;
import com.bopera.pointofsales.user.model.request.UpdateUser;
import com.bopera.pointofsales.user.model.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUser createUser) {
        return ResponseEntity.ok(
            new UserResponse(
                userService.save(createUser.mapToUserDetails())
            )
        );
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> update(@RequestBody UpdateUser updateUser) {
        return ResponseEntity.ok(
            new UserResponse(
                userService.save(updateUser.mapToUserDetails())
            )
        );
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(
        @RequestParam String currentPassword,
        @RequestParam String newPassword) {

        try {
            userService.updatePassword(currentPassword, newPassword);
        } catch (HttpClientErrorException exception) {
            return ResponseEntity.status(exception.getStatusCode())
                .body(exception.getMessage());
        }

        return ResponseEntity.ok("Password updated successfully");
    }
}
