package com.bopera.pointofsales.presentation;

import com.bopera.pointofsales.entity.User;
import com.bopera.pointofsales.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bopera.pointofsales.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> user() {
        List<User> users = userService.getUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public Object user(@PathVariable("id") Integer id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return ResponseEntity.ok(user);
    }
}
