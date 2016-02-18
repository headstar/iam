package com.headstartech.iam.core.jpa.services;

import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.common.exceptions.IAMNotFoundException;
import com.headstartech.iam.core.jpa.entities.DomainEntity;
import com.headstartech.iam.core.jpa.repositories.JpaDomainRepository;
import com.headstartech.iam.core.services.DomainService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.UUID;

@Service
public class JpaDomainService implements DomainService {

    private final JpaDomainRepository domainRepo;

    @Autowired
    public JpaDomainService(JpaDomainRepository domainRepo) {
        this.domainRepo = domainRepo;
    }

    @Override
    public String createDomain(@Valid Domain domain) throws IAMException {
        if (StringUtils.isNotBlank(domain.getId()) && domainRepo.exists(domain.getId())) {
            throw new IAMException("A domain with id " + domain.getId() + " already exists");
        }

        final DomainEntity domainEntity = new DomainEntity();
        domainEntity.setId(StringUtils.isBlank(domainEntity.getId()) ? UUID.randomUUID().toString() : domainEntity.getId());

        return domainRepo.save(domainEntity).getId();
    }

    @Override
    public Domain getDomain(String id) throws IAMException {
        return findDomain(id).getDTO();
    }

    private DomainEntity findDomain(final String id) throws IAMException {
        final DomainEntity domainEntity= domainRepo.findOne(id);
        if (domainEntity!= null) {
            return domainEntity;
        } else {
            throw new IAMNotFoundException("No domain with id " + id + " exists.");
        }
    }
}
