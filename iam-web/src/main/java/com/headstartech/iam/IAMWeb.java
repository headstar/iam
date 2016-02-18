package com.headstartech.iam;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class IAMWeb {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(IAMWeb.class, args);
    }
}