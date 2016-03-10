package com.headstartech.iam.web.controllers;

import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.core.jpa.repositories.JpaPermissionRepository;
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
public class PermissionControllerTest extends ControllerTestBase {

    @Autowired
    private JpaPermissionRepository jpaPermissionRepository;

    private Domain domain;

    @Before
    public void setup() {
        domain = createDomain();
    }

    @Test
    public void canCreateWithoutId() {
        Permission request = new Permission();
        request.setName(UUID.randomUUID().toString());

        long permissionCountBefore = jpaPermissionRepository.count();

        Permission response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/domains/{domainId}/permissions", domain.getId())
                .then()
                .log().ifValidationFails()
                .statusCode(201).extract().as(Permission.class);

        long permissionCountAfter = jpaPermissionRepository.count();

        assertFalse(response.getId().isEmpty());
        assertEquals(request.getName(), response.getName());
        assertEquals(1, permissionCountAfter - permissionCountBefore);
        assertTrue(jpaPermissionRepository.exists(response.getId()));
    }

    @Test
    public void canCreateWithId() {
        Permission request = new Permission();
        request.setId(UUID.randomUUID().toString());
        request.setName(UUID.randomUUID().toString());

        long permissionCountBefore = jpaPermissionRepository.count();

        Permission response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/domains/{domainId}/permissions", domain.getId())
                .then()
                .statusCode(201).extract().as(Permission.class);

        long permissionCountAfter = jpaPermissionRepository.count();

        assertFalse(response.getId().isEmpty());
        assertEquals(request.getName(), response.getName());
        assertEquals(1, permissionCountAfter - permissionCountBefore);
        assertTrue(jpaPermissionRepository.exists(request.getId()));
    }

    @Test
    public void canGetExisting() {
        Permission user = createPermission(UUID.randomUUID().toString());

        Permission response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .get("/api/domains/{domainId}/permissions/{permissionId}", domain.getId(), user.getId())
                .then()
                .statusCode(200).extract().as(Permission.class);

        assertEquals(user.getId(), response.getId());
        assertEquals(response.getName(), response.getName());
    }

    @Test
    public void canUpdateExisting() {
        Permission user = createPermission(UUID.randomUUID().toString());

        user.setName(UUID.randomUUID().toString());

        Permission response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(user)
                .put("/api/domains/{domainId}/permissions/{permissionId}", domain.getId(), user.getId())
                .then()
                .statusCode(200).extract().as(Permission.class);

        assertEquals(user.getId(), response.getId());
        assertEquals(user.getName(), response.getName());
    }

    @Test
    public void canDeleteExisting() {
        Permission user = createPermission(UUID.randomUUID().toString());

        given()
                .delete("/api/domains/{domainId}/permissions/{permissionId}", domain.getId(), user.getId())
                .then()
                .statusCode(204).extract();

        assertFalse(jpaPermissionRepository.exists(user.getId()));
    }

    private Permission createPermission(String id) {
        Permission request = new Permission();
        request.setId(id);
        request.setName(UUID.randomUUID().toString());

        Permission response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/domains/{domainId}/permissions", domain.getId())
                .then()
                .statusCode(201).extract().as(Permission.class);

        assertTrue(jpaPermissionRepository.exists(response.getId()));
        return response;
    }

}
