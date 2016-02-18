package com.headstartech.iam.web.controllers;

import com.headstartech.iam.common.dto.User;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.core.services.UserService;
import com.headstartech.iam.web.hateoas.assemblers.UserResourceAssembler;
import com.headstartech.iam.web.hateoas.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/domains/{domainId}/users")
public class UserRestController {

    private final UserService userService;
    private final UserResourceAssembler userResourceAssembler;

    @Autowired
    public UserRestController(UserService userService, UserResourceAssembler userResourceAssembler) {
        this.userService = userService;
        this.userResourceAssembler = userResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createUser(@PathVariable("domainId") final String domainId, @RequestBody final User user) throws IAMException {
        final String id = userService.createUser(domainId, user);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(id)
                        .toUri()
        );
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserResource getUser(@PathVariable("domainId") final String domainId, @PathVariable("userId") final String userId) throws IAMException {
        return this.userResourceAssembler.toResource(userService.getUser(domainId, userId));
    }
}
