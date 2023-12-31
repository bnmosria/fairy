package com.bopera.pointofsales.user.controller;

import com.bopera.pointofsales.domain.model.UserDetails;
import com.bopera.pointofsales.domain.service.PersistenceUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = {LoginUserController.class})
@AutoConfigureMockMvc
@ContextConfiguration(classes = {
    PersistenceUserService.class,
})
@EnableWebMvc
class LoginUserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersistenceUserService userService;

    @Test
    @WithMockUser
    void shouldReturnsListOfUserNamesWhenUserListIsNotEmpty() throws Exception {
        List<UserDetails> expectedUserList = List.of(
            UserDetails.builder().username("John").build(),
            UserDetails.builder().username("Jane").build()
        );

        when(userService.getUserList()).thenReturn(expectedUserList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/name-list")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jane"));
    }

    @Test
    @WithMockUser
    void shouldReturnsNotFoundWhenUserListIsEmpty() throws Exception {
        List<UserDetails> emptyUserList = new ArrayList<>();

        when(userService.getUserList()).thenReturn(emptyUserList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/name-list")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
