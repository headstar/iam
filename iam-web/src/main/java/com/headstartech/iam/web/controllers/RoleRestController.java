package com.headstartech.iam.web.controllers;

import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.core.services.RoleService;
import com.headstartech.iam.web.hateoas.assemblers.RoleResourceAssembler;
import com.headstartech.iam.web.hateoas.resources.RoleResource;
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

@RestController
@RequestMapping(value = "/domains/{domainId}/roles")
public class RoleRestController {

    private final RoleService roleService;
    private final RoleResourceAssembler roleResourceAssembler;

    @Autowired
    public RoleRestController(RoleService roleService, RoleResourceAssembler roleResourceAssembler) {
        this.roleService = roleService;
        this.roleResourceAssembler = roleResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createRole(@PathVariable("domainId") final String domainId, @RequestBody final Role user) throws IAMException {
        final String id = roleService.createRole(domainId, user);
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

    @RequestMapping(method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PagedResources<RoleResource> getRoles(@PageableDefault(page = 0, size = 10) final Pageable page,
                                                 final PagedResourcesAssembler<Role> assembler) {
        return assembler.toResource(
                roleService.getRoles(page),
                roleResourceAssembler);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RoleResource getRole(@PathVariable("domainId") final String domainId, @PathVariable("userId") final String userId) throws IAMException {
        return this.roleResourceAssembler.toResource(roleService.getRole(domainId, userId));
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRole(@PathVariable("domainId") final String domainId, @PathVariable("userId") final String userId, @RequestBody final Role user)
     throws IAMException {
        roleService.updateRole(domainId, userId, user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable("domainId") final String domainId, @PathVariable("userId") final String userId)
            throws IAMException {
        roleService.deleteRole(domainId, userId);
    }
}
