package com.headstartech.iam.web.hateoas.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.headstartech.iam.common.dto.Domain;
import org.springframework.hateoas.Resource;

public class DomainResource extends Resource<Domain> {

    @JsonCreator
    public DomainResource(Domain command) {
        super(command);
    }
}
