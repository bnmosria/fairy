package com.bopera.pointofsales.user.controller;

import com.bopera.pointofsales.domain.interfaces.UserServiceInterface;
import com.bopera.pointofsales.domain.model.UserDetails;
import com.bopera.pointofsales.user.model.request.CreateUser;
import com.bopera.pointofsales.user.model.request.UpdateUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {UserController.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EnableWebMvc
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceInterface userService;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void ShouldCreateUser_Correctly() throws Exception {
        CreateUser createUser = new CreateUser();
        createUser.setUsername("testUser");
        createUser.setActive(1);
        createUser.setPassword("password");

        UserDetails userDetails = createUser.mapToUserDetails();
        userDetails.setId(234L);

        when(userService.save(any())).thenReturn(userDetails);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\",\"active\":1,\"password\":\"password\"}"))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"id\":234,\"userName\":\"testUser\",\"active\":1,\"roles\":null}"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void ShouldUpdateUser_Correctly() throws Exception {
        UpdateUser updateUser = new UpdateUser();
        updateUser.setId(1L);
        updateUser.setUsername("updatedUser");
        updateUser.setActive(1);

        when(userService.save(any())).thenReturn(updateUser.mapToUserDetails());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"username\":\"updatedUser\",\"active\":1}"))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"id\":1,\"userName\":\"updatedUser\",\"active\":1,\"roles\":null}"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void ShouldDeleteUser_Correctly() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1")
                .with(csrf()))
            .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"STAFF"})
    void ShouldUpdatePassword_WhenCurrentPasswordIsCorrect() throws Exception {
        doNothing().when(userService).updatePassword("currentPassword", "newPassword");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/updatePassword")
                .with(csrf())
                .param("currentPassword", "currentPassword")
                .param("newPassword", "newPassword"))
            .andExpect(status().isOk())
            .andExpect(content().string("Password updated successfully"));
    }

    @Test
    @WithMockUser(roles = {"STAFF"})
    void ShouldNotUpdatedPassword_WhenCurrentPasswordIsIncorrect_ThenReturnsBadRequest() throws Exception {
        doThrow(new HttpClientErrorException(
            HttpStatus.BAD_REQUEST,
            "Current password is incorrect"
        ))
            .when(userService)
            .updatePassword("wrongPassword", "newPassword");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/updatePassword")
                .with(csrf())
                .param("currentPassword", "wrongPassword")
                .param("newPassword", "newPassword"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"GUEST"})
    void ShouldDenyAccess_WhenRoleNotAllowed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/updatePassword")
                .param("currentPassword", "currentPassword")
                .param("newPassword", "newPassword"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void ShouldUpdatePassword_WhenUserHasAdminRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/updatePassword")
                .with(csrf())
                .param("currentPassword", "currentPassword")
                .param("newPassword", "newPassword"))
            .andExpect(status().isOk());
    }
}
