package com.headstartech.iam.core.services;

import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.exceptions.IAMException;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface DomainService {

    String createDomain(@Valid final Domain domain) throws IAMException;

    Domain getDomain(@NotBlank(message = "No id entered, unable to get domain")
                     final String id) throws IAMException;

    Page<Domain> getDomains(Pageable page);

}
