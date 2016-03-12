package com.headstartech.iam.web.controllers;

import com.headstartech.iam.common.dto.User;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.common.resources.RoleResources;
import com.headstartech.iam.core.services.UserService;
import com.headstartech.iam.web.hateoas.assemblers.RoleResourceAssembler;
import com.headstartech.iam.web.hateoas.assemblers.UserResourceAssembler;
import com.headstartech.iam.common.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/domains/{domainId}/users")
public class UserRestController {

    private final UserService userService;
    private final UserResourceAssembler userResourceAssembler;
    private final RoleResourceAssembler roleResourceAssembler;

    @Autowired
    public UserRestController(UserService userService, UserResourceAssembler userResourceAssembler, RoleResourceAssembler roleResourceAssembler) {
        this.userService = userService;
        this.userResourceAssembler = userResourceAssembler;
        this.roleResourceAssembler = roleResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResource> createUser(@PathVariable("domainId") final String domainId, @RequestBody final User user) throws IAMException {
        final String id = userService.createUser(domainId, user);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(id)
                        .toUri()
        );
        return new ResponseEntity<>(userResourceAssembler.toResource(userService.getUser(domainId, id)), httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PagedResources<UserResource> getUsers(@PathVariable("domainId") final String domainId, @PageableDefault(page = 0, size = 10) final Pageable page,
                                                 final PagedResourcesAssembler<User> assembler) {
        return assembler.toResource(
                userService.getUsers(domainId, page),
                userResourceAssembler);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserResource getUser(@PathVariable("domainId") final String domainId, @PathVariable("userId") final String userId) throws IAMException {
        return userResourceAssembler.toResource(userService.getUser(domainId, userId));
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserResource updateUser(@PathVariable("domainId") final String domainId, @PathVariable("userId") final String userId, @RequestBody final User user)
     throws IAMException {
        userService.updateUser(domainId, userId, user);
        return userResourceAssembler.toResource(userService.getUser(domainId, userId));
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("domainId") final String domainId, @PathVariable("userId") final String userId)
            throws IAMException {
        userService.deleteUser(domainId, userId);
    }

    @RequestMapping(value = "/{userId}/roles", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRoles(@PathVariable("domainId") final String domainId, @PathVariable("userId") final String userId, @RequestBody Set<String> roleIds)
            throws IAMException {
        userService.addRoles(domainId, userId, roleIds);
    }

    @RequestMapping(value = "/{userId}/roles", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setRoles(@PathVariable("domainId") final String domainId, @PathVariable("userId") final String userId, @RequestBody Set<String> roleIds)
            throws IAMException {
        userService.setRoles(domainId, userId, roleIds);
    }

    @RequestMapping(value = "/{userId}/roles", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RoleResources getRoles(@PathVariable("domainId") final String domainId, @PathVariable("userId") final String userId)
            throws IAMException {
        return new RoleResources(roleResourceAssembler.toResources(userService.getRoles(domainId, userId)));
    }

    @RequestMapping(value = "/{userId}/roles", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllRoles(@PathVariable("domainId") final String domainId, @PathVariable("userId") final String userId)
            throws IAMException {
        userService.removeAllRoles(domainId, userId);
    }

}
