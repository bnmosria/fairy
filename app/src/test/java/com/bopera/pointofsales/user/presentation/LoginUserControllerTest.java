package com.bopera.pointofsales.user.presentation;

import com.bopera.pointofsales.user.model.response.LoginUserResponse;
import com.bopera.pointofsales.user.service.LoginUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginUserControllerTest {

    @Mock
    private LoginUserService userService;

    private LoginUserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new LoginUserController(userService);
    }

    @Test
    void shouldReturnsListOfUserNamesWhenUserListIsNotEmpty() {
        List<LoginUserResponse> expectedUserList = List.of(
            LoginUserResponse.builder().name("John").build(),
            LoginUserResponse.builder().name("Jane").build()
        );

        when(userService.getLoginUserList()).thenReturn(expectedUserList);

        ResponseEntity<List<LoginUserResponse>> response = userController.getUserNameList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUserList, response.getBody());
        verify(userService, times(1)).getLoginUserList();
    }

    @Test
    void shouldReturnsNotFoundWhenUserListIsEmpty() {
        when(userService.getLoginUserList()).thenReturn(List.of());

        ResponseEntity<List<LoginUserResponse>> response = userController.getUserNameList();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getLoginUserList();
    }
}
