package com.bopera.pointofsales.user.presentation;

import com.bopera.pointofsales.exception.ResourceNotFoundException;
import com.bopera.pointofsales.user.model.request.CreateUser;
import com.bopera.pointofsales.user.model.request.UpdateUser;
import com.bopera.pointofsales.user.model.response.UserResponse;
import com.bopera.pointofsales.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUser createUser) {
        return ResponseEntity.ok(userService.create(createUser));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> update(@RequestBody UpdateUser updateUser)
        throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.update(updateUser));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
