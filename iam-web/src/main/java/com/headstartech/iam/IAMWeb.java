package com.headstartech.iam;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
public class IAMWeb {

    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication(IAMWeb.class);
        springApplication.addListeners(new ApplicationEnvVerifier());
        springApplication.run(args);
    }
}