package com.headstartech.iam.web.hateoas.assemblers;

import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.web.hateoas.resources.PermissionResource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissionResourceAssembler  implements ResourceAssembler<Permission, PermissionResource> {

    @Override
    public PermissionResource toResource(Permission role) {
        final PermissionResource permissionResource = new PermissionResource(role);
       /* try {
            permissionResource.add(
                    ControllerLinkBuilder.linkTo(
                            ControllerLinkBuilder.methodOn(UserRestController.class)
                                    .getDomain(role.getId()))
                            .withSelfRel()
            );
        } catch (final IAMException ge) {
            // Force a server exception
            throw new RuntimeException(ge);
        }*/

        return permissionResource;
    }
}
