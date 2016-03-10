package com.headstartech.iam.web.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.headstartech.iam.IAMWeb;
import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.core.jpa.services.RandomString;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;

@ActiveProfiles({"integration"})
@SpringApplicationConfiguration(classes = IAMWeb.class)
@WebIntegrationTest(randomPort = true)
public class ControllerTestBase {

    @Value("${local.server.port}")
    private int port;

    @BeforeClass
    public static void baseClassSetup() {
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
                ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    mapper.registerModule(new Jackson2HalModule());
                    return mapper;
                }));
    }

    @Before
    public void baseBeforeTestSetup() {
        RestAssured.port = port;
    }

    protected Domain createDomain() {
        return createDomain(RandomString.randomId());
    }

    protected Domain createDomain(String id) {
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
