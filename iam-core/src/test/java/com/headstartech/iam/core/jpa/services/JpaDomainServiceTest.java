package com.headstartech.iam.core.jpa.services;

import com.headstartech.iam.common.dto.AuthenticateRequest;
import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.exceptions.IAMBadRequestException;
import com.headstartech.iam.common.exceptions.IAMConflictException;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.common.exceptions.IAMNotFoundException;
import com.headstartech.iam.core.services.DomainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
public class JpaDomainServiceTest extends TestBase {

    @Autowired
    private DomainService domainService;

    @Test(expected = IAMConflictException.class)
    public void cannotCreateWithExistingId() throws IAMException {
        // given
        String id = UUID.randomUUID().toString();
        Domain firstDomain = new Domain();
        firstDomain.setId(id);
        domainService.createDomain(firstDomain);

        Domain secondDomain = new Domain();
        secondDomain.setId(id);

        // when
        domainService.createDomain(secondDomain);


        // then... exception should be thrown
    }

    @Test(expected = IAMNotFoundException.class)
    public void cannotUpdateNonExistingDomain() throws IAMException {
        // given
        String id = UUID.randomUUID().toString();
        Domain domain = new Domain();
        domain.setId(id);

        // when
        domainService.updateDomain(id, domain);

        // then... exception should be thrown
    }

    @Test(expected = IAMBadRequestException.class)
    public void cannotUpdateWithInconsistentIds() throws IAMException {
        // given
        String id = UUID.randomUUID().toString();
        Domain domain = new Domain();
        domain.setId(id);
        domainService.createDomain(domain);

        domain.setId("a");
        // when
        domainService.updateDomain(id, domain);

        // then... exception should be thrown
    }

    @Test(expected = IAMNotFoundException.class)
    public void cannotGetNonExistingDomain() throws IAMException {
        // given
        String id = UUID.randomUUID().toString();

        // when
        domainService.getDomain(id);

        // then... exception should be thrown
    }

    @Test(expected = IAMNotFoundException.class)
    public void cannotDeleteNonExistingDomain() throws IAMException {
        // given
        String id = UUID.randomUUID().toString();

        // when
        domainService.deleteDomain(id);

        // then... exception should be thrown
    }

    @Test(expected = IAMNotFoundException.class)
    public void cannotAuthenticateForNonExistingDomain() throws IAMException {
        // given
        String id = UUID.randomUUID().toString();
        AuthenticateRequest request = new AuthenticateRequest();
        request.setUserName("user");
        request.setPassword("secret");

        // when
        domainService.authenticateUser(id, request);

        // then... exception should be thrown
    }

    @Test(expected = IAMNotFoundException.class)
    public void cannotAuthenticateNonExistingUser() throws IAMException {
        // given
        String id = UUID.randomUUID().toString();
        Domain domain = new Domain();
        domain.setId(id);
        domainService.createDomain(domain);
        AuthenticateRequest request = new AuthenticateRequest();
        request.setUserName("user");
        request.setPassword("secret");

        // when
        domainService.authenticateUser(id, request);

        // then... exception should be thrown
    }
}
