package com.headstartech.iam.web.controllers;

import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.dto.User;
import com.headstartech.iam.common.resources.RoleResources;
import com.headstartech.iam.core.jpa.repositories.JpaUserRepository;
import com.headstartech.iam.common.resources.UserResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest extends ControllerTestBase {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Test
    public void canCreateWithoutId() {
        User request = new User();
        request.setUserName(UUID.randomUUID().toString());
        request.setPassword("asecret");
        request.setAttributes(new HashMap<>());
        request.getAttributes().put("a", "b");

        long userCountBefore = jpaUserRepository.count();

        User response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/domains/{domainId}/users", domain.getId())
                .then()
                .log().ifValidationFails()
                .statusCode(201).extract().as(User.class);

        long userCountAfter = jpaUserRepository.count();

        assertFalse(response.getId().isEmpty());
        assertEquals(request.getUserName(), response.getUserName());
        assertNull(response.getPassword());
        assertEquals(request.getAttributes(), response.getAttributes());
        assertEquals(1, userCountAfter - userCountBefore);
        assertTrue(jpaUserRepository.exists(response.getId()));
    }

    @Test
    public void canCreateWithId() {
        User request = new User();
        request.setId(UUID.randomUUID().toString());
        request.setUserName(UUID.randomUUID().toString());
        request.setPassword("asecret");
        request.setAttributes(new HashMap<>());
        request.getAttributes().put("a", "b");

        long userCountBefore = jpaUserRepository.count();

        User response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/domains/{domainId}/users", domain.getId())
                .then()
                .statusCode(201).extract().as(User.class);

        long userCountAfter = jpaUserRepository.count();

        assertFalse(response.getId().isEmpty());
        assertNull(response.getPassword());
        assertEquals(request.getAttributes(), response.getAttributes());
        assertEquals(1, userCountAfter - userCountBefore);
        assertTrue(jpaUserRepository.exists(request.getId()));
    }

    @Test
    public void canGetExisting() {
        User user = createUser();

        User response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .get("/api/domains/{domainId}/users/{userId}", domain.getId(), user.getId())
                .then()
                .statusCode(200).extract().as(User.class);

        assertEquals(user.getId(), response.getId());
        assertEquals(user.getAttributes(), response.getAttributes());
    }

    @Test
    public void canUpdateExisting() {
        User user = createUser();

        user.getAttributes().put(UUID.randomUUID().toString(), "a");

        User response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(user)
                .put("/api/domains/{domainId}/users/{userId}", domain.getId(), user.getId())
                .then()
                .statusCode(200).extract().as(User.class);

        assertEquals(user.getId(), response.getId());
        assertEquals(user.getAttributes(), response.getAttributes());
    }

    @Test
    public void canDeleteExisting() {
        User user = createUser();

        given()
                .delete("/api/domains/{domainId}/users/{userId}", domain.getId(), user.getId())
                .then()
                .statusCode(204).extract();

        assertFalse(jpaUserRepository.exists(user.getId()));
    }

    @Test
    public void canAddRoles() {
        User user = createUser();

        Role role1 = createRole();
        Set<String> firstRole = new HashSet<>();
        firstRole.add(role1.getId());

        // add a role
        given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(firstRole)
                .post("/api/domains/{domainId}/users/{userId}/roles", domain.getId(), user.getId())
                .then()
                .statusCode(204);

        RoleResources assignedRoles = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .get("/api/domains/{domainId}/users/{userId}/roles", domain.getId(), user.getId())
                .then()
                .statusCode(200).extract().as(RoleResources.class);

        assertEquals(1, assignedRoles.getContent().size());
        assignedRoles.getContent().stream().forEach(r -> r.getContent().getId().equals(role1.getId()));

        // add another role
        Role role2 = createRole();
        Set<String> secondRole = new HashSet<>();
        secondRole.add(role2.getId());
        given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(secondRole)
                .post("/api/domains/{domainId}/users/{userId}/roles", domain.getId(), user.getId())
                .then()
                .statusCode(204);

        assignedRoles = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .get("/api/domains/{domainId}/users/{userId}/roles", domain.getId(), user.getId())
                .then()
                .statusCode(200).extract().as(RoleResources.class);

        assertEquals(2, assignedRoles.getContent().size());
        List<String> ids = assignedRoles.getContent().stream().map(r -> r.getContent().getId()).collect(Collectors.toList());
        assertTrue(ids.contains(role1.getId()));
        assertTrue(ids.contains(role2.getId()));
    }

    @Test
    public void canSetRoles() {
        User user = createUser();

        Role role1 = createRole();
        Set<String> firstRole = new HashSet<>();
        firstRole.add(role1.getId());

        // add a role
        given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(firstRole)
                .post("/api/domains/{domainId}/users/{userId}/roles", domain.getId(), user.getId())
                .then()
                .statusCode(204);

        // set another role
        Role role2 = createRole();
        Set<String> secondRole = new HashSet<>();
        secondRole.add(role2.getId());
        given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(secondRole)
                .put("/api/domains/{domainId}/users/{userId}/roles", domain.getId(), user.getId())
                .then()
                .statusCode(204);

        RoleResources assignedRoles = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .get("/api/domains/{domainId}/users/{userId}/roles", domain.getId(), user.getId())
                .then()
                .statusCode(200).extract().as(RoleResources.class);

        assertEquals(1, assignedRoles.getContent().size());
        List<String> ids = assignedRoles.getContent().stream().map(r -> r.getContent().getId()).collect(Collectors.toList());
        assertTrue(ids.contains(role2.getId()));
    }

    @Test
    public void canRemoveRoles() {
        User user = createUser();

        Role role1 = createRole();
        Set<String> roleIds = new HashSet<>();
        roleIds.add(role1.getId());

        // add a role
        given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(roleIds)
                .post("/api/domains/{domainId}/users/{userId}/roles", domain.getId(), user.getId())
                .then()
                .statusCode(204);

        // delete roles
        given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .delete("/api/domains/{domainId}/users/{userId}/roles", domain.getId(), user.getId())
                .then()
                .statusCode(204);

        RoleResources assignedRoles = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .get("/api/domains/{domainId}/users/{userId}/roles", domain.getId(), user.getId())
                .then()
                .statusCode(200).extract().as(RoleResources.class);
        assertEquals(0, assignedRoles.getContent().size());
    }

    @Test
    public void getPage() {
        // create a few users to be sure get have more than 1 page
        createUser();
        createUser();
        createUser();

        PagedUserResources response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .get("/api/domains/{domainId}/users?size=2", domain.getId())
                .then()
                .statusCode(200).extract().as(PagedUserResources.class);

        response.getContent().stream().forEach(r -> assertNotNull(r.getContent().getId()));
        assertEquals(0, response.getMetadata().getNumber());
        assertEquals(2, response.getMetadata().getSize());
        assertTrue(response.getMetadata().getTotalElements() >= 3);
        assertEquals(2, response.getContent().size());
    }

    static class PagedUserResources extends PagedResources<UserResource> {

    }



}
