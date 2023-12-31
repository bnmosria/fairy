package com.bopera.pointofsales.user.controller;

import com.bopera.pointofsales.domain.model.UserDetails;
import com.bopera.pointofsales.domain.service.PersistenceUserService;
import com.bopera.pointofsales.user.model.response.LoginUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginUserControllerTest {

    @Mock
    private PersistenceUserService userService;

    private LoginUserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new LoginUserController(userService);
    }

    @Test
    void shouldReturnsListOfUserNamesWhenUserListIsNotEmpty() {
        List<UserDetails> expectedUserList = List.of(
            UserDetails.builder().username("John").build(),
            UserDetails.builder().username("Jane").build()
        );

        when(userService.getUserList()).thenReturn(expectedUserList);

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
