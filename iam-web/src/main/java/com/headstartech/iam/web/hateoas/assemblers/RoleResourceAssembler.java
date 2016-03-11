package com.headstartech.iam.web.hateoas.assemblers;

import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.web.controllers.RoleRestController;
import com.headstartech.iam.common.resources.RoleResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RoleResourceAssembler extends ResourceAssemblerSupport<Role, RoleResource> {

    public RoleResourceAssembler() {
        super(RoleRestController.class, RoleResource.class);
    }

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
