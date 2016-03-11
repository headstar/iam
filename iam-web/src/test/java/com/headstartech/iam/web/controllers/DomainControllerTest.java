package com.headstartech.iam.web.controllers;

import com.headstartech.iam.common.dto.AuthenticateRequest;
import com.headstartech.iam.common.dto.AuthenticateResponse;
import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.dto.User;
import com.headstartech.iam.common.resources.PagedDomainResources;
import com.headstartech.iam.core.jpa.repositories.JpaDomainRepository;
import com.headstartech.iam.common.resources.DomainResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class DomainControllerTest extends ControllerTestBase {

    @Autowired
    private JpaDomainRepository jpaDomainRepository;

    @Test
    public void canCreateWithoutId() {
        Domain request = new Domain();
        request.setDescription("abc");

        long domainCountBefore = jpaDomainRepository.count();

        Domain response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/domains")
                .then()
                .statusCode(201).extract().as(Domain.class);

        long domainCountAfter = jpaDomainRepository.count();

        assertFalse(response.getId().isEmpty());
        assertEquals(request.getDescription(), response.getDescription());
        assertEquals(1, domainCountAfter - domainCountBefore);
        assertTrue(jpaDomainRepository.exists(response.getId()));
    }

    @Test
    public void canCreateWithId() {
        Domain request = new Domain();
        request.setId(UUID.randomUUID().toString());
        request.setDescription("abc");

        long domainCountBefore = jpaDomainRepository.count();

        Domain response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/domains")
                .then()
                .statusCode(201).extract().as(Domain.class);

        long domainCountAfter = jpaDomainRepository.count();

        assertFalse(response.getId().isEmpty());
        assertEquals(request.getDescription(), response.getDescription());
        assertEquals(1, domainCountAfter - domainCountBefore);
        assertTrue(jpaDomainRepository.exists(request.getId()));
    }

    @Test
    public void canGetExisting() {
        Domain domain = createDomain(UUID.randomUUID().toString());

        Domain response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .get("/api/domains/{id}", domain.getId())
                .then()
                .statusCode(200).extract().as(Domain.class);

        assertEquals(domain.getId(), response.getId());
        assertEquals(domain.getDescription(), response.getDescription());
    }

    @Test
    public void canUpdateExisting() {
        Domain domain = createDomain(UUID.randomUUID().toString());

        domain.setDescription(domain.getDescription() + "a");

        Domain response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(domain)
                .put("/api/domains/{id}", domain.getId())
                .then()
                .statusCode(200).extract().as(Domain.class);

        assertEquals(domain.getId(), response.getId());
        assertEquals(domain.getDescription(), response.getDescription());
    }

    @Test
    public void canDeleteExisting() {
        Domain domain = createDomain(UUID.randomUUID().toString());
        assertTrue(jpaDomainRepository.exists(domain.getId()));

        given()
                .delete("/api/domains/{id}", domain.getId())
                .then()
                .statusCode(204).extract();

        assertFalse(jpaDomainRepository.exists(domain.getId()));
    }

    @Test
    public void canDeleteAll() {
        createDomain(UUID.randomUUID().toString());
        assertTrue(jpaDomainRepository.count() > 0);

        given()
                .delete("/api/domains")
                .then()
                .statusCode(204).extract();

        assertEquals(0, jpaDomainRepository.count());
    }

    @Test
    public void canAuthenticate() {
        Domain domain = createDomain(UUID.randomUUID().toString());
        User user = createUser(domain.getId(), "mysecret");

        AuthenticateRequest authenticateRequest = new AuthenticateRequest();
        authenticateRequest.setUserName(user.getUserName());
        authenticateRequest.setPassword("mysecret");

        AuthenticateResponse authenticateResponse = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(authenticateRequest)
                .post("/api/domains/{domainId}/authenticate", domain.getId())
                .then()
                .statusCode(200).extract().as(AuthenticateResponse.class);

        assertTrue(authenticateResponse.isAuthenticationSuccessful());

    }

    @Test
    public void getPage() {
        // create a few domains to be sure get have more than 1 page
        createDomain();
        createDomain();
        createDomain();

        PagedDomainResources response = given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .get("/api/domains?size=2")
                .then()
                .statusCode(200).extract().as(PagedDomainResources.class);

        response.getContent().stream().forEach(r -> assertNotNull(r.getContent().getId()));
        assertEquals(0, response.getMetadata().getNumber());
        assertEquals(2, response.getMetadata().getSize());
        assertTrue(response.getMetadata().getTotalElements() >= 3);
        assertEquals(2, response.getContent().size());
    }
}
