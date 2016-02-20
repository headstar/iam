package com.headstartech.iam.core.jpa.services;

import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.exceptions.IAMBadRequestException;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.common.exceptions.IAMNotFoundException;
import com.headstartech.iam.core.jpa.entities.DomainEntity;
import com.headstartech.iam.core.jpa.entities.RoleEntity;
import com.headstartech.iam.core.jpa.repositories.JpaDomainRepository;
import com.headstartech.iam.core.jpa.repositories.JpaRoleRepository;
import com.headstartech.iam.core.services.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(rollbackFor = { Exception.class })
public class JpaRoleService implements RoleService {

    private final JpaDomainRepository domainRepo;
    private final JpaRoleRepository roleRepo;

    @Autowired
    public JpaRoleService(JpaDomainRepository domainRepo, JpaRoleRepository roleRepo) {
        this.domainRepo = domainRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public String createRole(String domainId, Role role) throws IAMException {
        DomainEntity domainEntity = findDomain(domainId);

        final RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(StringUtils.isBlank(role.getId()) ? UUID.randomUUID().toString() : role.getId());
        roleEntity.setName(role.getName());
        roleEntity.setDomain(domainEntity);
        return roleRepo.save(roleEntity).getId();
    }

    @Override
    public Page<Role> getRoles(Pageable page) {
        Page<RoleEntity> userEntities = roleRepo.findAll(page);
        return userEntities.map(RoleEntity::getDTO);
    }

    @Override
    public Role getRole(String domainId, String userId) throws IAMException {
        RoleEntity roleEntity = findRole(domainId, userId);
        return roleEntity.getDTO();
    }

    @Override
    public void updateRole(String domainId, String userId, Role role) throws IAMException {
        RoleEntity roleEntity = findRole(domainId, userId);
        if (!userId.equals(role.getId())) {
            throw new IAMBadRequestException("Role id inconsistent with id passed in.");
        }

        roleEntity.setName(role.getName());
        roleRepo.save(roleEntity);
    }

    @Override
    public void deleteRole(String domainId, String userId) throws IAMException {
        RoleEntity roleEntity = findRole(domainId, userId);
        roleEntity.getDomain().removeRole(roleEntity);
        roleRepo.delete(roleEntity);
    }

    private RoleEntity findRole(final String domainId, final String userId) throws IAMException {
        final RoleEntity roleEntity = roleRepo.findOne(userId);
        if (roleEntity!= null) {
            if(!roleEntity.getDomain().getId().equals(domainId)) {
                throw new IAMNotFoundException("No role with id " + userId + " exists.");
            }

            return roleEntity;
        } else {
            throw new IAMNotFoundException("No role with id " + userId + " exists.");
        }
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
