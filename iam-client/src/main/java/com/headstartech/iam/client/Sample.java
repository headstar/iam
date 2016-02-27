package com.headstartech.iam.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.exceptions.IAMException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class Sample {

    public static void main(String[] args) throws IAMException, IOException {
        RestOperations restOperations = new RestTemplate();
        IAMClient iamClient = new DefaultIAMClient(restOperations, "http://localhost:8080");
        Domain d = new Domain();
        d.setDescription("A nice domain too!");
        Domain f = iamClient.createDomain(d);
        f.setDescription("zzz");
        Domain g = iamClient.updateDomain(f);
        Domain j = iamClient.getDomain(g.getId());
    }
}
