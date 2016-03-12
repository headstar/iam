package com.headstartech.iam.common.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

public class RoleResources extends Resources<RoleResource> {
    public RoleResources() {
    }

    public RoleResources(Iterable<RoleResource> content, Link... links) {
        super(content, links);
    }

    public RoleResources(Iterable<RoleResource> content, Iterable<Link> links) {
        super(content, links);
    }
}
