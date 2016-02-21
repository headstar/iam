package com.headstartech.iam.core.jpa.services;

import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.exceptions.IAMBadRequestException;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.common.exceptions.IAMNotFoundException;
import com.headstartech.iam.core.annotations.TransactionalService;
import com.headstartech.iam.core.jpa.entities.DomainEntity;
import com.headstartech.iam.core.jpa.entities.PermissionEntity;
import com.headstartech.iam.core.jpa.entities.RoleEntity;
import com.headstartech.iam.core.jpa.repositories.JpaDomainRepository;
import com.headstartech.iam.core.jpa.repositories.JpaPermissionRepository;
import com.headstartech.iam.core.jpa.repositories.JpaRoleRepository;
import com.headstartech.iam.core.services.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@TransactionalService
public class JpaRoleService implements RoleService {

    private final JpaDomainRepository domainRepo;
    private final JpaRoleRepository roleRepo;
    private final JpaPermissionRepository permissionRepository;

    @Autowired
    public JpaRoleService(JpaDomainRepository domainRepo, JpaRoleRepository roleRepo, JpaPermissionRepository permissionRepository) {
        this.domainRepo = domainRepo;
        this.roleRepo = roleRepo;
        this.permissionRepository = permissionRepository;
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
        Page<RoleEntity> roleEntities = roleRepo.findAll(page);
        return roleEntities.map(RoleEntity::getDTO);
    }

    @Override
    public Role getRole(String domainId, String roleId) throws IAMException {
        RoleEntity roleEntity = findRole(domainId, roleId);
        return roleEntity.getDTO();
    }

    @Override
    public void updateRole(String domainId, String roleId, Role role) throws IAMException {
        RoleEntity roleEntity = findRole(domainId, roleId);
        if (!roleId.equals(role.getId())) {
            throw new IAMBadRequestException("Role id inconsistent with id passed in.");
        }

        roleEntity.setName(role.getName());
        roleRepo.save(roleEntity);
    }

    @Override
    public void deleteRole(String domainId, String roleId) throws IAMException {
        RoleEntity roleEntity = findRole(domainId, roleId);
        roleRepo.delete(roleEntity);
    }

    @Override
    public Set<Permission> getPermissions(String domainId, String roleId) throws IAMException {
        RoleEntity roleEntity = findRole(domainId, roleId);
        return roleEntity.getPermissions().stream().map(PermissionEntity::getDTO).collect(Collectors.toSet());
    }

    @Override
    public void addPermissions(String domainId, String roleId, Set<String> permissionIds) throws IAMException {
        RoleEntity roleEntity = findRole(domainId, roleId);
        for(String permissionId : permissionIds) {
            PermissionEntity permissionEntity = findPermission(domainId, permissionId);
            roleEntity.addPermission(permissionEntity);
        }
    }

    @Override
    public void setPermissions(String domainId, String roleId, Set<String> permissionIds) throws IAMException {
        RoleEntity roleEntity = findRole(domainId, roleId);
        Set<PermissionEntity> permissionEntities = new HashSet<>();
        for(String permissionId : permissionIds) {
            permissionEntities.add(findPermission(domainId, permissionId));
        }
        roleEntity.setPermissions(permissionEntities);
    }

    @Override
    public void removeAllPermissions(String domainId, String roleId) throws IAMException {
        RoleEntity roleEntity = findRole(domainId, roleId);
        roleEntity.getPermissions().clear();
    }

    private RoleEntity findRole(final String domainId, final String roleId) throws IAMException {
        final RoleEntity roleEntity = roleRepo.findOne(roleId);
        if (roleEntity!= null) {
            if(!roleEntity.getDomain().getId().equals(domainId)) {
                throw new IAMNotFoundException("No role with id " + roleId + " exists.");
            }

            return roleEntity;
        } else {
            throw new IAMNotFoundException("No role with id " + roleId + " exists.");
        }
    }

    private PermissionEntity findPermission(final String domainId, final String permissionId) throws IAMException {
        final PermissionEntity permissionEntity = permissionRepository.findOne(permissionId);
        if (permissionEntity!= null) {
            if(!permissionEntity.getDomain().getId().equals(domainId)) {
                throw new IAMNotFoundException("No permission with id " + permissionId + " exists.");
            }

            return permissionEntity;
        } else {
            throw new IAMNotFoundException("No permission with id " + permissionId + " exists.");
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
