package com.headstartech.iam.web.controllers;

import com.headstartech.iam.IAMWeb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IAMWeb.class)
@WebIntegrationTest(randomPort = true)
public class DomainControllerTest {

    // Since we're bringing the service up on random port need to figure out what it is
    @Value("${local.server.port}")
    private int port;

    @Test
    public void foo() {

    }
}
