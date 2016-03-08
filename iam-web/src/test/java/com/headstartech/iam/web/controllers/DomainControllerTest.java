package com.headstartech.iam.web.controllers;

import com.headstartech.iam.IAMWeb;
import com.headstartech.iam.core.jpa.repositories.JpaDomainRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"integration"})
@SpringApplicationConfiguration(classes = IAMWeb.class)
@WebIntegrationTest(randomPort = true)
public class DomainControllerTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private JpaDomainRepository jpaDomainRepository;

    @Test
    public void foo() {
        Assert.assertThat(this.jpaDomainRepository.count(), Matchers.is(0L));
    }
}
