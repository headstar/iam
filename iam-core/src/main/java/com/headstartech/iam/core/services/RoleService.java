package com.headstartech.iam.core.services;

import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.exceptions.IAMException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Set;

@Validated
public interface RoleService {

    String createRole(String domainId, @Valid final Role role) throws IAMException;

    Role getRole(String domainId, String roleId) throws IAMException;

    void updateRole(final String domainId, final String roleId,
                    @Valid final Role role) throws IAMException;

    void deleteRole(String domainId, String roleId) throws IAMException;

    Page<Role> getRoles(String domainId, Pageable page) throws IAMException;

    Set<Permission> getPermissions(String domainId, String roleId) throws IAMException;

    void addPermissions(String domainId, String roleId, Set<String> permissionIds) throws IAMException;

    void setPermissions(String domainId, String roleId, Set<String> permissionIds) throws IAMException;

    void removeAllPermissions(String domainId, String roleId) throws IAMException;
}
