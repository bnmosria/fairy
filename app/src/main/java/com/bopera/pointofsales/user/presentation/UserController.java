package com.bopera.pointofsales.user.presentation;

import com.bopera.pointofsales.auth.model.LoginUser;
import com.bopera.pointofsales.entity.User;
import com.bopera.pointofsales.user.service.LoginUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final LoginUserService userService;

    public UserController(LoginUserService userService) {
        this.userService = userService;
    }

    @GetMapping("name-list")
    public ResponseEntity<List<LoginUser>> getUserNameList() {
        List<LoginUser> userNameList = userService.getLoginUserList();

        if (userNameList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userNameList);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('Admin')")
    public void addNewUser(@RequestBody User userInfo) {

    }

    @GetMapping("/{id}/profile")
    public String userProfile(@PathVariable String id) {
        return "Welcome to User Profile for id: " + id;
    }

}
