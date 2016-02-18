package com.headstartech.iam.web.hateoas.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.headstartech.iam.common.dto.User;
import org.springframework.hateoas.Resource;

public class UserResource extends Resource<User> {

    @JsonCreator
    public UserResource(User user) {
        super(user);
    }
}
