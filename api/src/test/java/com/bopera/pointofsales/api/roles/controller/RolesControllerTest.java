package com.bopera.pointofsales.api.roles.controller;

import com.bopera.pointofsales.api.roles.model.request.RoleRequest;
import com.bopera.pointofsales.api.roles.model.response.RoleResponse;
import com.bopera.pointofsales.domain.interfaces.RoleService;
import com.bopera.pointofsales.domain.model.Role;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@WebFluxTest(RolesController.class)
@Import(SecurityConfig.class)
public class RolesControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private RoleService roleService;

    @BeforeEach
    public void setup() {
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void ShouldReturnsRoleResponse_WhenValidRoleRequest() {
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setName("FOO_ROLE");

        Role role = Role.builder().id(1L).name("FOO_ROLE").build();

        when(roleService.addRole(any(Role.class))).thenReturn(role);

        webClient.mutateWith(csrf())
            .post()
            .uri("/api/roles")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(roleRequest), RoleRequest.class)
            .exchange()
            .expectStatus().isOk()
            .expectBody(RoleResponse.class)
            .isEqualTo(RoleResponse.builder().id(1L).name("FOO_ROLE").build());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void ShouldReturn400Status_WhenRoleRequestWithNullName() {
        RoleRequest roleRequest = new RoleRequest();

        webClient.mutateWith(csrf())
            .post()
            .uri("/api/roles")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(roleRequest), RoleRequest.class)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void ShouldReturn400Status_WhenRoleRequestWithEmptyName() {
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setName("");

        webClient.mutateWith(csrf()).post()
            .uri("/api/roles")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(roleRequest), RoleRequest.class)
            .exchange()
            .expectStatus().isBadRequest();
    }
}
