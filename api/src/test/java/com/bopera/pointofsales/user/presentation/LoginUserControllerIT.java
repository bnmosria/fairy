package com.bopera.pointofsales.user.presentation;

import com.bopera.pointofsales.user.model.response.LoginUserResponse;
import com.bopera.pointofsales.user.service.LoginUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LoginUserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginUserService userService;

    @Test
    void shouldReturnsListOfUserNamesWhenUserListIsNotEmpty() throws Exception {
        List<LoginUserResponse> expectedUserList = List.of(
            LoginUserResponse.builder().name("John").build(),
            LoginUserResponse.builder().name("Jane").build()
        );

        when(userService.getLoginUserList()).thenReturn(expectedUserList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/name-list")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jane"));
    }

    @Test
    void shouldReturnsNotFoundWhenUserListIsEmpty() throws Exception {
        List<LoginUserResponse> emptyUserList = new ArrayList<>();

        when(userService.getLoginUserList()).thenReturn(emptyUserList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/name-list")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
