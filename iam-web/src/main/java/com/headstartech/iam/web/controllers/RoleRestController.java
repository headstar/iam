package com.headstartech.iam.web.controllers;

import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.core.services.RoleService;
import com.headstartech.iam.web.hateoas.assemblers.PermissionResourceAssembler;
import com.headstartech.iam.web.hateoas.assemblers.RoleResourceAssembler;
import com.headstartech.iam.web.hateoas.resources.PermissionResource;
import com.headstartech.iam.web.hateoas.resources.RoleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Set;

@RestController
@RequestMapping(value = "/domains/{domainId}/roles")
public class RoleRestController {

    private final RoleService roleService;
    private final RoleResourceAssembler roleResourceAssembler;
    private final PermissionResourceAssembler permissionResourceAssembler;

    @Autowired
    public RoleRestController(RoleService roleService, RoleResourceAssembler roleResourceAssembler, PermissionResourceAssembler permissionResourceAssembler) {
        this.roleService = roleService;
        this.roleResourceAssembler = roleResourceAssembler;
        this.permissionResourceAssembler = permissionResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createRole(@PathVariable("domainId") final String domainId, @RequestBody final Role role) throws IAMException {
        final String id = roleService.createRole(domainId, role);
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

    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RoleResource getRole(@PathVariable("domainId") final String domainId, @PathVariable("roleId") final String roleId) throws IAMException {
        return this.roleResourceAssembler.toResource(roleService.getRole(domainId, roleId));
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRole(@PathVariable("domainId") final String domainId, @PathVariable("roleId") final String roleId, @RequestBody final Role role)
     throws IAMException {
        roleService.updateRole(domainId, roleId, role);
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable("domainId") final String domainId, @PathVariable("roleId") final String roleId)
            throws IAMException {
        roleService.deleteRole(domainId, roleId);
    }

    @RequestMapping(value = "/{roleId}/permissions", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<PermissionResource> getPermissions(@PathVariable("domainId") final String domainId, @PathVariable("roleId") final String roleId) throws IAMException {
        Resources<PermissionResource> resources = new Resources<>(permissionResourceAssembler.toResources(roleService.getPermissions(domainId, roleId)));
        return resources;
    }

    @RequestMapping(value = "/{roleId}/permissions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addPermissions(@PathVariable("domainId") final String domainId, @PathVariable("roleId") final String roleId, @RequestBody Set<String> permissionIds) throws IAMException {
        roleService.addPermissions(domainId, roleId, permissionIds);
    }

    @RequestMapping(value = "/{roleId}/permissions", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setPermissions(@PathVariable("domainId") final String domainId, @PathVariable("roleId") final String roleId, @RequestBody Set<String> permissionIds) throws IAMException {
        roleService.setPermissions(domainId, roleId, permissionIds);
    }

    @RequestMapping(value = "/{roleId}/permissions", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllPermissions(@PathVariable("domainId") final String domainId, @PathVariable("roleId") final String roleId) throws IAMException {
        roleService.removeAllPermissions(domainId, roleId);
    }

}
