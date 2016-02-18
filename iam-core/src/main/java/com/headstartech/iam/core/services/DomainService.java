package com.headstartech.iam.core.services;

import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.exceptions.IAMException;

public interface DomainService {

    String createDomain(final Domain domain) throws IAMException;

    Domain getDomain(final String id) throws IAMException;

}
