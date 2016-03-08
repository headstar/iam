package com.headstartech.iam.web.controllers;

import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.core.services.PermissionService;
import com.headstartech.iam.core.services.RoleService;
import com.headstartech.iam.web.hateoas.assemblers.PermissionResourceAssembler;
import com.headstartech.iam.web.hateoas.assemblers.RoleResourceAssembler;
import com.headstartech.iam.web.hateoas.resources.PermissionResource;
import com.headstartech.iam.web.hateoas.resources.RoleResource;
import com.headstartech.iam.web.hateoas.resources.UserResource;
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
@RequestMapping(value = "/api/domains/{domainId}/permissions")
public class PermissionRestController {

    private final PermissionService permissionService;
    private final PermissionResourceAssembler permissionResourceAssembler;

    @Autowired
    public PermissionRestController(PermissionService permissionService, PermissionResourceAssembler permissionResourceAssembler) {
        this.permissionService = permissionService;
        this.permissionResourceAssembler = permissionResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PermissionResource> createPermission(@PathVariable("domainId") final String domainId, @RequestBody final Permission permission) throws IAMException {
        final String id = permissionService.createPermission(domainId, permission);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(id)
                        .toUri()
        );
        return new ResponseEntity<>(permissionResourceAssembler.toResource(permissionService.getPermission(domainId, id)), httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PagedResources<PermissionResource> getPermissions(@PathVariable("domainId") final String domainId, @PageableDefault(page = 0, size = 10) final Pageable page,
                                                             final PagedResourcesAssembler<Permission> assembler) {
        return assembler.toResource(
                permissionService.getPermissions(domainId, page),
                permissionResourceAssembler);
    }

    @RequestMapping(value = "/{permissionId}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PermissionResource getPermission(@PathVariable("domainId") final String domainId, @PathVariable("permissionId") final String permissionId) throws IAMException {
        return permissionResourceAssembler.toResource(permissionService.getPermission(domainId, permissionId));
    }

    @RequestMapping(value = "/{permissionId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PermissionResource updatePermission(@PathVariable("domainId") final String domainId, @PathVariable("permissionId") final String permissionId, @RequestBody final Permission permission)
            throws IAMException {
        permissionService.updatePermission(domainId, permissionId, permission);
        return permissionResourceAssembler.toResource(permissionService.getPermission(domainId, permissionId));
    }

    @RequestMapping(value = "/{permissionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePermission(@PathVariable("domainId") final String domainId, @PathVariable("permissionId") final String permissionId)
            throws IAMException {
        permissionService.deletePermission(domainId, permissionId);
    }
}
