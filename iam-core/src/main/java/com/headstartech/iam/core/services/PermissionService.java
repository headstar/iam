package com.headstartech.iam.core.services;

import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.common.exceptions.IAMException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface PermissionService {

    String createPermission(String domainId, @Valid final Permission role) throws IAMException;

    Permission getPermission(String domainId, String userId) throws IAMException;

    void updatePermission(final String domainId, final String userId,
                    @Valid final Permission user) throws IAMException;

    void deletePermission(String domainId, String userId) throws IAMException;

    Page<Permission> getPermissions(Pageable page);
}
