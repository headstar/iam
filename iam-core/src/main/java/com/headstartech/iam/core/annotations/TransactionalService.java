package com.headstartech.iam.core.annotations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Service
@Transactional(rollbackFor = { Exception.class })
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface TransactionalService {
}
