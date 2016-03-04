package com.headstartech.iam.web;

import com.headstartech.iam.core.annotations.Dev;
import com.headstartech.iam.core.annotations.Prod;
import com.headstartech.iam.core.annotations.QA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 */
public class ApplicationEnvVerifier implements ApplicationListener {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationEnvVerifier.class);

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ApplicationEnvironmentPreparedEvent) {
            ApplicationEnvironmentPreparedEvent event = (ApplicationEnvironmentPreparedEvent) applicationEvent;
            Environment env = event.getEnvironment();
            Set<String> res = Arrays.stream(env.getActiveProfiles()).filter(new Predicate<String>() {
                @Override
                public boolean test(String s) {
                    return Dev.name.equals(s) || QA.name.equals(s) || Prod.name.equals(s);
                }
            }).collect(Collectors.toSet());

            if(res.isEmpty() ||res.size() > 1) {
                logger.error("Exactly one of {}, {}, {} profiles must be set as active!", Dev.name, QA.name, Prod.name);
                System.exit(1);
            }
        }
    }

}

