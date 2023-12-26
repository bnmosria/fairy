package com.bopera.pointofsales.user.presentation;

import com.bopera.pointofsales.user.model.response.LoginUserResponse;
import com.bopera.pointofsales.user.service.LoginUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class LoginUserController {

    private final LoginUserService userService;

    public LoginUserController(LoginUserService userService) {
        this.userService = userService;
    }

    @GetMapping("name-list")
    public ResponseEntity<List<LoginUserResponse>> getUserNameList() {
        List<LoginUserResponse> userNameList = userService.getLoginUserList();

        if (userNameList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userNameList);
    }

}
