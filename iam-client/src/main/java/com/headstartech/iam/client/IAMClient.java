package com.headstartech.iam.client;

import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.exceptions.IAMException;

public interface IAMClient {

    Domain createDomain(Domain domain) throws IAMException;

    Domain getDomain(String id);

    Domain updateDomain(Domain domain);

    void deleteDomain(String id);
}
