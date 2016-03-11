package com.headstartech.iam.common.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.headstartech.iam.common.dto.User;
import org.springframework.hateoas.Resource;

public class UserResource extends Resource<User> {

    @JsonCreator
    public UserResource(User user) {
        super(user);
    }
}
