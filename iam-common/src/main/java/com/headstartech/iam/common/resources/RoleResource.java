package com.headstartech.iam.common.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.headstartech.iam.common.dto.Role;
import org.springframework.hateoas.Resource;

public class RoleResource extends Resource<Role> {

    @JsonCreator
    public RoleResource(Role role) {
        super(role);
    }
}
