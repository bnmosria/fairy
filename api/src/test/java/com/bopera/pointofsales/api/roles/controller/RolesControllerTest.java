package com.bopera.pointofsales.api.roles.controller;

import com.bopera.pointofsales.domain.interfaces.RoleService;
import com.bopera.pointofsales.domain.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {RolesController.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EnableWebMvc
public class RolesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void ShouldReturnsRoleResponse_WhenValidRoleRequest() throws Exception {
        Role role = Role.builder().id(1L).name("FOO_ROLE").build();

        when(roleService.addRole(any(Role.class))).thenReturn(role);

        mockMvc.perform(post("/api/roles")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"FOO_ROLE\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(role.getId()))
            .andExpect(jsonPath("$.name").value(role.getName()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void ShouldReturn400Status_WhenRoleRequestWithNullName() throws Exception {
        mockMvc.perform(post("/api/roles")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":null}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void ShouldReturn400Status_WhenRoleRequestWithEmptyName() throws Exception {
        mockMvc.perform(post("/api/roles")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\"}"))
            .andExpect(status().isBadRequest());
    }
}
