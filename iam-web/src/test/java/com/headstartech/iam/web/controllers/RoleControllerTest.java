package com.headstartech.iam.web.controllers;

import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.core.jpa.repositories.JpaPermissionRepository;
import com.headstartech.iam.core.jpa.repositories.JpaRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class RoleControllerTest extends ControllerTestBase {

    @Autowired
    private JpaRoleRepository jpaRoleRepository;

    private Domain domain;

    @Before
    public void setup() {
        domain = createDomain();
    }

    @Test
    public void canCreateWithoutId() {
        Role request = new Role();
        request.setName(UUID.randomUUID().toString());

        long permissionCountBefore = jpaRoleRepository.count();

        Role response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/domains/{domainId}/roles", domain.getId())
                .then()
                .log().ifValidationFails()
                .statusCode(201).extract().as(Role.class);

        long permissionCountAfter = jpaRoleRepository.count();

        assertFalse(response.getId().isEmpty());
        assertEquals(request.getName(), response.getName());
        assertEquals(1, permissionCountAfter - permissionCountBefore);
        assertTrue(jpaRoleRepository.exists(response.getId()));
    }

    @Test
    public void canCreateWithId() {
        Role request = new Role();
        request.setId(UUID.randomUUID().toString());
        request.setName(UUID.randomUUID().toString());

        long permissionCountBefore = jpaRoleRepository.count();

        Role response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/domains/{domainId}/roles", domain.getId())
                .then()
                .statusCode(201).extract().as(Role.class);

        long permissionCountAfter = jpaRoleRepository.count();

        assertFalse(response.getId().isEmpty());
        assertEquals(request.getName(), response.getName());
        assertEquals(1, permissionCountAfter - permissionCountBefore);
        assertTrue(jpaRoleRepository.exists(request.getId()));
    }

    @Test
    public void canGetExisting() {
        Role user = createRole(UUID.randomUUID().toString());

        Role response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .get("/api/domains/{domainId}/roles/{roleId}", domain.getId(), user.getId())
                .then()
                .statusCode(200).extract().as(Role.class);

        assertEquals(user.getId(), response.getId());
        assertEquals(response.getName(), response.getName());
    }

    @Test
    public void canUpdateExisting() {
        Role user = createRole(UUID.randomUUID().toString());

        user.setName(UUID.randomUUID().toString());

        Role response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(user)
                .put("/api/domains/{domainId}/roles/{roleId}", domain.getId(), user.getId())
                .then()
                .statusCode(200).extract().as(Role.class);

        assertEquals(user.getId(), response.getId());
        assertEquals(user.getName(), response.getName());
    }

    @Test
    public void canDeleteExisting() {
        Role user = createRole(UUID.randomUUID().toString());

        given()
                .delete("/api/domains/{domainId}/roles/{roleId}", domain.getId(), user.getId())
                .then()
                .statusCode(204).extract();

        assertFalse(jpaRoleRepository.exists(user.getId()));
    }

    private Role createRole(String id) {
        Role request = new Role();
        request.setId(id);
        request.setName(UUID.randomUUID().toString());

        Role response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/domains/{domainId}/roles", domain.getId())
                .then()
                .statusCode(201).extract().as(Role.class);

        assertTrue(jpaRoleRepository.exists(response.getId()));
        return response;
    }

}
