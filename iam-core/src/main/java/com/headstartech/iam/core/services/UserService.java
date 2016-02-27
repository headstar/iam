package com.headstartech.iam.core.services;

import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.dto.User;
import com.headstartech.iam.common.exceptions.IAMException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Set;

@Validated
public interface UserService {

    String createUser(String domainId, @Valid User user) throws IAMException;

    User getUser(String domainId, String userId) throws IAMException;

    void updateUser(final String domainId, final String userId,
                      @Valid final User user) throws IAMException;

    void deleteUser(String domainId, String userId) throws IAMException;

    Page<User> getUsers(String domainId, Pageable page);

    void addRoles(String domainId, String userId, Set<String> roleIds) throws IAMException;

    Set<Role> getRoles(String domainId, String userId) throws IAMException;

    void removeAllRoles(String domainId, String userId) throws IAMException;
}
