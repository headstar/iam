package com.headstartech.iam.web.hateoas.assemblers;

import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.web.controllers.PermissionRestController;
import com.headstartech.iam.common.resources.PermissionResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PermissionResourceAssembler extends ResourceAssemblerSupport<Permission, PermissionResource> {

    public PermissionResourceAssembler() {
        super(PermissionRestController.class, PermissionResource.class);
    }

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
