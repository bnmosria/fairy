package com.bopera.pointofsales.user.controller;

import com.bopera.pointofsales.domain.interfaces.UserServiceInterface;
import com.bopera.pointofsales.domain.model.User;
import com.bopera.pointofsales.user.model.response.LoginUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserControllerTest {

    @Mock
    private UserServiceInterface userService;

    private LoginUserController userController;

    @BeforeEach
    void setUp() {
        userController = new LoginUserController(userService);
    }

    @Test
    void shouldReturnsListOfUserNamesWhenUserListIsNotEmpty() {
        List<User> userDetailsList = List.of(
            User.builder().username("John").build(),
            User.builder().username("Jane").build()
        );

        when(userService.getUserList()).thenReturn(userDetailsList);

        List<LoginUserResponse> expectedUserList = userDetailsList.stream().map(
            userDetails -> LoginUserResponse.builder()
                .name(userDetails.getUsername()
                ).build()).toList();

        ResponseEntity<List<LoginUserResponse>> response = userController.getUserNameList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUserList, response.getBody());
        verify(userService, times(1)).getUserList();
    }

    @Test
    void shouldReturnsNotFoundWhenUserListIsEmpty() {
        when(userService.getUserList()).thenReturn(List.of());

        ResponseEntity<List<LoginUserResponse>> response = userController.getUserNameList();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUserList();
    }
}
