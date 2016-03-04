package com.headstartech.iam;

import com.headstartech.iam.web.ApplicationEnvVerifier;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class IAMWeb {

    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication(IAMWeb.class);
        springApplication.addListeners(new ApplicationEnvVerifier());
        springApplication.run(args);
    }
}