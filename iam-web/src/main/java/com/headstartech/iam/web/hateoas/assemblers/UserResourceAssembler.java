package com.headstartech.iam.web.hateoas.assemblers;

import com.headstartech.iam.common.dto.User;
import com.headstartech.iam.common.resources.UserResource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, UserResource> {

    @Override
    public UserResource toResource(User user) {
        final UserResource userResource = new UserResource(user);
       /* try {
            userResource.add(
                    ControllerLinkBuilder.linkTo(
                            ControllerLinkBuilder.methodOn(UserRestController.class)
                                    .getDomain(user.getId()))
                            .withSelfRel()
            );
        } catch (final IAMException ge) {
            // Force a server exception
            throw new RuntimeException(ge);
        }*/

        return userResource;
    }
}
