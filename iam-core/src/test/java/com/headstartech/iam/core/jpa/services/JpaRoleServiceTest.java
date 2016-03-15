package com.headstartech.iam.core.jpa.services;

import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.exceptions.IAMBadRequestException;
import com.headstartech.iam.common.exceptions.IAMConflictException;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.common.exceptions.IAMNotFoundException;
import com.headstartech.iam.core.services.DomainService;
import com.headstartech.iam.core.services.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
public class JpaRoleServiceTest extends TestBase {

    @Autowired
    private DomainService domainService;

    @Autowired
    private RoleService roleService;

    private String domainId;
    private String roleId;
    private Role role;

    @Before
    public void setup() throws IAMException {
        role = new Role();
        role.setName(UUID.randomUUID().toString());
        Domain domain = new Domain();
        domainId = domainService.createDomain(domain);
        roleId = roleService.createRole(domainId, role);
        role = roleService.getRole(domainId, roleId);
    }

    @Test(expected = IAMConflictException.class)
    public void cannotCreateWithExistingId() throws IAMException {
        // given
        Role aRole = new Role();
        aRole.setId(role.getId());
        aRole.setName(UUID.randomUUID().toString());

        // when
        roleService.createRole(domainId, aRole);

        // then... exception should be thrown
    }

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

    @Test(expected = IAMNotFoundException.class)
    public void cannotUpdateRoleForNonExistingDomain() throws IAMException {
        // given

        // when
        roleService.updateRole(UUID.randomUUID().toString(), role.getId(), role);

        // then...exception should be thrown
    }

    @Test(expected = IAMBadRequestException.class)
    public void cannotUpdateRoleWithInconsistentIds() throws IAMException {
        // given
        role.setId("foo");

        // when
        roleService.updateRole(domainId, roleId, role);

        // then...exception should be thrown
    }

    @Test(expected = IAMNotFoundException.class)
    public void cannotDeleteRoleForNonExistingDomain() throws IAMException {
        // given

        // when
        roleService.deleteRole(UUID.randomUUID().toString(), roleId);

        // then...exception should be thrown
    }

    @Test(expected = IAMNotFoundException.class)
    public void cannotDeleteNonExistingRole() throws IAMException {
        // given

        // when
        roleService.deleteRole(domainId, UUID.randomUUID().toString());

        // then...exception should be thrown
    }

    @Test(expected = IAMNotFoundException.class)
    public void cannotGetRolesForNonExistingDomain() throws IAMException {
        // given
        PageRequest pageRequest = new PageRequest(0, 10);

        // when
        roleService.getRoles(UUID.randomUUID().toString(), pageRequest);

        // then...exception should be thrown
    }
}
