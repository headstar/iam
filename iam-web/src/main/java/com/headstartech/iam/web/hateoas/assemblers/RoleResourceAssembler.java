package com.headstartech.iam.web.hateoas.assemblers;

import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.web.hateoas.resources.RoleResource;
import com.headstartech.iam.web.hateoas.resources.UserResource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class RoleResourceAssembler implements ResourceAssembler<Role, RoleResource> {

    @Override
    public RoleResource toResource(Role role) {
        final RoleResource roleResource = new RoleResource(role);
       /* try {
            roleResource.add(
                    ControllerLinkBuilder.linkTo(
                            ControllerLinkBuilder.methodOn(UserRestController.class)
                                    .getDomain(role.getId()))
                            .withSelfRel()
            );
        } catch (final IAMException ge) {
            // Force a server exception
            throw new RuntimeException(ge);
        }*/

        return roleResource;
    }
}
