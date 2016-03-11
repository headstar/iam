package com.headstartech.iam.common.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.headstartech.iam.common.dto.Domain;
import org.springframework.hateoas.Resource;

public class DomainResource extends Resource<Domain> {

    @JsonCreator
    public DomainResource(Domain domain) {
        super(domain);
    }
}
