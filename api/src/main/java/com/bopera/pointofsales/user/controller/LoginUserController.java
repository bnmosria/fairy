package com.bopera.pointofsales.user.controller;

import com.bopera.pointofsales.domain.interfaces.UserServiceInterface;
import com.bopera.pointofsales.user.model.response.LoginUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
class LoginUserController {

    private final UserServiceInterface userService;

    LoginUserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @GetMapping("nameList")
    ResponseEntity<List<LoginUserResponse>> getUserNameList() {
        List<LoginUserResponse> userNameList = userService.getUserList()
            .stream().map(
                userDetails -> LoginUserResponse.builder()
                    .name(userDetails.getUsername()
            ).build()).toList();

        if (userNameList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userNameList);
    }

}
