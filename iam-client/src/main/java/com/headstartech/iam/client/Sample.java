package com.headstartech.iam.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.dto.User;
import com.headstartech.iam.common.exceptions.IAMException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class Sample {

    public static void main(String[] args) throws IAMException, IOException {
        RestOperations restOperations = new RestTemplate();
        IAMClient iamClient = new DefaultIAMClient(restOperations, "http://localhost:8080");
        Domain d = new Domain();
        d.setDescription("A super domain!");
        d = iamClient.createDomain(d);

        User u = new User();
        u.setUserName("u1");
        u.setPassword("secret");
        u = iamClient.createUser(d.getId(), u);

        Role r = new Role();
        r.setName("ADMIN");
        r = iamClient.createRole(d.getId(), r);

        Permission p = new Permission();
        p.setName("READ");
        p = iamClient.createPermission(d.getId(), p);
    }
}
