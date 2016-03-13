package com.headstartech.iam.core.jpa.services;

import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.common.exceptions.IAMNotFoundException;
import com.headstartech.iam.core.services.DomainService;
import com.headstartech.iam.core.services.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
public class JpaRoleServiceTest extends TestBase {

    @Autowired
    private DomainService domainService;

    @Autowired
    private RoleService roleService;

    @Test(expected = IAMNotFoundException.class)
    public void cannotCreateRoleForNonExistingDomain() throws IAMException {
        // given
        Role role = new Role();
        role.setName("aRole");

        // when
        roleService.createRole(UUID.randomUUID().toString(), role);

        // then...exception should be thrown
    }

    @Test(expected = IAMNotFoundException.class)
    public void cannotGetRoleForNonExistingDomain() throws IAMException {
        // given
        Role role = new Role();
        role.setName("aRole");
        Domain domain = new Domain();
        String domainId = domainService.createDomain(domain);
        String roleId = roleService.createRole(domainId, role);

        // when
        roleService.getRole(UUID.randomUUID().toString(), roleId);

        // then...exception should be thrown
    }

    @Test(expected = IAMNotFoundException.class)
    public void cannotGetNonExistingRole() throws IAMException {
        // given
        Domain domain = new Domain();
        String domainId = domainService.createDomain(domain);

        // when
        roleService.getRole(domainId, UUID.randomUUID().toString());

        // then...exception should be thrown
    }

}
