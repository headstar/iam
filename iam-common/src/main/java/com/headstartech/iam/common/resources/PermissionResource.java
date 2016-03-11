package com.headstartech.iam.common.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.headstartech.iam.common.dto.Permission;
import org.springframework.hateoas.Resource;

public class PermissionResource extends Resource<Permission> {

    @JsonCreator
    public PermissionResource(Permission permission) {
        super(permission);
    }
}
