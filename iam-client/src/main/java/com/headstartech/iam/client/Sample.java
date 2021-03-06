package com.headstartech.iam.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.dto.User;
import com.headstartech.iam.common.exceptions.IAMException;
import org.apache.http.HttpHost;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.core.AnnotationRelProvider;
import org.springframework.hateoas.core.DefaultRelProvider;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

public class Sample {

    public static void main(String[] args) throws IAMException, IOException {
        HttpHost targetHost = new HttpHost("localhost", 8080, "http");
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactoryBasicAuth(targetHost, "user", "user");
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(jsonConverter());
        converters.add(halConverter());
        restTemplate.setMessageConverters(converters);

        IAMClient iamClient = new DefaultIAMClient(restTemplate, "http://localhost:8080/api");

        iamClient.getDomains();

        Domain d = new Domain();
        d.setDescription("A super domain!");
        d = iamClient.createDomain(d);

        User u = new User();
        u.setUserName("u1");
        u.setPassword("secret");
        iamClient.createUser(d.getId(), u);

        iamClient.getUsers(d.getId());

        Role r = new Role();
        r.setName("ADMIN");
        r = iamClient.createRole(d.getId(), r);

        iamClient.getRoles(d.getId());

        Permission p1 = new Permission();
        p1.setName("READ");
        p1 = iamClient.createPermission(d.getId(), p1);

        Permission p2 = new Permission();
        p2.setName("WRITE");
        p2 = iamClient.createPermission(d.getId(), p2);

        iamClient.getPermissions(d.getId());

        Set<String> ps = new HashSet<>();
        ps.add(p1.getId());
        ps.add(p2.getId());
        iamClient.setPermissionsForRole(d.getId(), r.getId(), ps);

        iamClient.getPermissionsForRole(d.getId(), r.getId());

    }
    private static MappingJackson2HttpMessageConverter jsonConverter() {
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jacksonConverter.setObjectMapper(mapper);
        return jacksonConverter;
    }

    private static MappingJackson2HttpMessageConverter halConverter() {
    /*    RelProvider defaultRelProvider = defaultRelProvider();
        RelProvider annotationRelProvider = annotationRelProvider();

        OrderAwarePluginRegistry<RelProvider, Class<?>> relProviderPluginRegistry = OrderAwarePluginRegistry
                .create(Arrays.asList(defaultRelProvider, annotationRelProvider));

        DelegatingRelProvider delegatingRelProvider = new DelegatingRelProvider(relProviderPluginRegistry);

        ObjectMapper halObjectMapper = new ObjectMapper();
        halObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        halObjectMapper.registerModule(new Jackson2HalModule());
        halObjectMapper.setHandlerInstantiator(new Jackson2HalModule.HalHandlerInstantiator(delegatingRelProvider, null, null));

        MappingJackson2HttpMessageConverter halConverter = new MappingJackson2HttpMessageConverter();
        halConverter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        halConverter.setObjectMapper(halObjectMapper);
        return halConverter;*/
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes(MediaTypes.HAL_JSON_VALUE));
        converter.setObjectMapper(mapper);
        return converter;
    }

    public static DefaultRelProvider defaultRelProvider() {
        return new DefaultRelProvider();
    }

    public static AnnotationRelProvider annotationRelProvider() {
        return new AnnotationRelProvider();
    }
}
