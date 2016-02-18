package com.headstartech.iam.web.controllers;

import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.core.services.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
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

    @Autowired
    public DomainRestController(DomainService domainService) {
        this.domainService = domainService;
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
    public Domain getDomain(@PathVariable("id") final String id) throws IAMException {
        return domainService.getDomain(id);
    }
}
