package com.headstartech.iam.common.resources;

import com.headstartech.iam.common.dto.Permission;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

public class PermissionResources extends Resources<PermissionResource> {

    public PermissionResources() {
    }

    public PermissionResources(Iterable<PermissionResource> content, Link... links) {
        super(content, links);
    }

    public PermissionResources(Iterable<PermissionResource> content, Iterable<Link> links) {
        super(content, links);
    }
}
