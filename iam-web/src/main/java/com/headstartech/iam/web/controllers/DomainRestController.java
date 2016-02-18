package com.headstartech.iam.web.controllers;

import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.core.services.DomainService;
import com.headstartech.iam.web.hateoas.assemblers.DomainResourceAssembler;
import com.headstartech.iam.web.hateoas.resources.DomainResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/domains")
public class DomainRestController {

    private final DomainService domainService;
    private final DomainResourceAssembler domainResourceAssembler;

    @Autowired
    public DomainRestController(DomainService domainService, DomainResourceAssembler domainResourceAssembler) {
        this.domainService = domainService;
        this.domainResourceAssembler = domainResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createCommand(@RequestBody final Domain domain) throws IAMException {
        final String id = domainService.createDomain(domain);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(id)
                        .toUri()
        );
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public DomainResource getDomain(@PathVariable("id") final String id) throws IAMException {
        return this.domainResourceAssembler.toResource(domainService.getDomain(id));
    }
}
