package com.headstartech.iam.web.hateoas.assemblers;

import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.web.controllers.DomainRestController;
import com.headstartech.iam.common.resources.DomainResource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class DomainResourceAssembler implements ResourceAssembler<Domain, DomainResource> {

    @Override
    public DomainResource toResource(Domain domain) {
        final DomainResource domainResource = new DomainResource(domain);
        try {
            domainResource.add(
                    ControllerLinkBuilder.linkTo(
                            ControllerLinkBuilder.methodOn(DomainRestController.class)
                                    .getDomain(domain.getId()))
                            .withSelfRel()
            );
        } catch (final IAMException ge) {
            // Force a server exception
            throw new RuntimeException(ge);
        }

        return domainResource;
    }
}
