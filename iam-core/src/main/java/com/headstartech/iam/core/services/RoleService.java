package com.headstartech.iam.core.services;

import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.exceptions.IAMException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface RoleService {

    String createRole(String domainId, @Valid final Role role) throws IAMException;

    Role getRole(String domainId, String userId) throws IAMException;

    void updateRole(final String domainId, final String userId,
                    @Valid final Role user) throws IAMException;

    void deleteRole(String domainId, String userId) throws IAMException;

    Page<Role> getRoles(Pageable page);
}
