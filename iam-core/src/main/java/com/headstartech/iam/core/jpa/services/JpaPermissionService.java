package com.headstartech.iam.core.jpa.services;

import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.common.exceptions.IAMBadRequestException;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.common.exceptions.IAMNotFoundException;
import com.headstartech.iam.core.annotations.TransactionalService;
import com.headstartech.iam.core.jpa.entities.DomainEntity;
import com.headstartech.iam.core.jpa.entities.PermissionEntity;
import com.headstartech.iam.core.jpa.repositories.JpaDomainRepository;
import com.headstartech.iam.core.jpa.repositories.JpaPermissionRepository;
import com.headstartech.iam.core.services.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@TransactionalService
public class JpaPermissionService implements PermissionService {

    private final JpaDomainRepository domainRepo;
    private final JpaPermissionRepository permissionRepo;

    @Autowired
    public JpaPermissionService(JpaDomainRepository domainRepo, JpaPermissionRepository permissionRepo) {
        this.domainRepo = domainRepo;
        this.permissionRepo = permissionRepo;
    }

    @Override
    public String createPermission(String domainId, Permission permission) throws IAMException {
        DomainEntity domainEntity = findDomain(domainId);

        final PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setId(StringUtils.isBlank(permission.getId()) ? UUID.randomUUID().toString() : permission.getId());
        permissionEntity.setName(permission.getName());
        permissionEntity.setDomain(domainEntity);
        return permissionRepo.save(permissionEntity).getId();
    }

    @Override
    public Page<Permission> getPermissions(Pageable page) {
        Page<PermissionEntity> userEntities = permissionRepo.findAll(page);
        return userEntities.map(PermissionEntity::getDTO);
    }

    @Override
    public Permission getPermission(String domainId, String userId) throws IAMException {
        PermissionEntity permissionEntity = findPermission(domainId, userId);
        return permissionEntity.getDTO();
    }

    @Override
    public void updatePermission(String domainId, String userId, Permission permission) throws IAMException {
        PermissionEntity permissionEntity = findPermission(domainId, userId);
        if (!userId.equals(permission.getId())) {
            throw new IAMBadRequestException("Permission id inconsistent with id passed in.");
        }

        permissionEntity.setName(permission.getName());
        permissionRepo.save(permissionEntity);
    }

    @Override
    public void deletePermission(String domainId, String userId) throws IAMException {
        PermissionEntity permissionEntity = findPermission(domainId, userId);
        permissionRepo.delete(permissionEntity);
    }

    private PermissionEntity findPermission(final String domainId, final String userId) throws IAMException {
        final PermissionEntity permissionEntity = permissionRepo.findOne(userId);
        if (permissionEntity!= null) {
            if(!permissionEntity.getDomain().getId().equals(domainId)) {
                throw new IAMNotFoundException("No permission with id " + userId + " exists.");
            }

            return permissionEntity;
        } else {
            throw new IAMNotFoundException("No permission with id " + userId + " exists.");
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
