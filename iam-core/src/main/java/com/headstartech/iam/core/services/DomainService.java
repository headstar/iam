package com.headstartech.iam.core.services;

import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.exceptions.IAMException;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface DomainService {

    String createDomain( @NotNull(message = "No domain entered. Unable to create.")
            @Valid final Domain domain) throws IAMException;

    Domain getDomain(@NotBlank(message = "No id entered, unable to get domain")
                     final String id) throws IAMException;

    void updateDomain(@NotBlank(message = "No id entered. Unable to update.")
                      final String id,
                      @NotNull(message = "No domain entered. Unable to update.")
                      @Valid final Domain domain) throws IAMException;

    void deleteDomain(
            @NotBlank(message = "No id entered. Unable to delete.")
            final String id
    ) throws IAMException;

    void deleteAllDomains() throws IAMException;

    Page<Domain> getDomains(Pageable page);

}
