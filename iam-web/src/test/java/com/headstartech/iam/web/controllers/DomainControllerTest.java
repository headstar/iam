package com.headstartech.iam.web.controllers;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.headstartech.iam.IAMWeb;
import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.core.jpa.repositories.JpaDomainRepository;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.mapper.factory.Jackson2ObjectMapperFactory;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"integration"})
@SpringApplicationConfiguration(classes = IAMWeb.class)
@WebIntegrationTest(randomPort = true)
public class DomainControllerTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private JpaDomainRepository jpaDomainRepository;

    @BeforeClass
    public static void setupClass() {
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
                ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    mapper.registerModule(new Jackson2HalModule());
                    return mapper;
                }));
    }

    @Before
    public void setup() {
        RestAssured.port = port;
    }

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

    Domain createDomain(String id) {
        Domain request = new Domain();
        request.setId(id);
        request.setDescription("desc");
        return given()
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/domains")
                .then()
                .statusCode(201).extract().as(Domain.class);
    }

}
