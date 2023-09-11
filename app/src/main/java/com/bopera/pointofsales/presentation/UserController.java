package com.bopera.pointofsales.presentation;

import com.bopera.pointofsales.entity.User;
import com.bopera.pointofsales.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/addNewUser")
    @PreAuthorize("hasAuthority('Admin')")
    public void addNewUser(@RequestBody User userInfo) {
        userService.addUser(userInfo);
    }

    @GetMapping("/{id}/profile")
    public String userProfile(@PathVariable String id) {
        return "Welcome to User Profile for id: " + id;
    }

}
