package com.headstartech.iam.client;

import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.dto.User;
import com.headstartech.iam.common.exceptions.IAMException;

import java.util.Set;

public interface IAMClient {

    Domain createDomain(Domain domain) throws IAMException;

    Domain getDomain(String id);

    Domain updateDomain(Domain domain);

    void deleteDomain(String id);

    Set<Domain> getDomains();

    User createUser(String domainId, User user);

    User getUser(String domainId, String userId);

    User updateUser(String domainId, User user);

    void deleteUser(String domainId, String userId);

    Set<User> getUsersForDomain(String domainId);

    Role createRole(String domainId, Role role);

    Role getRole(String domainId, String roleId);

    Role updateRole(String domainId, Role role);

    void deleteRole(String domainId, String RoleId);

    Set<Role> getRolesForDomain(String domainId);

    Permission createPermission(String domainId, Permission permissionId);

    Permission getPermission(String domainId, String permissionId);

    Permission updatePermission(String domainId, Permission permissionId);

    void deletePermission(String domainId, String permissionId);

    Set<Permission> getPermissionsForDomain(String domainId);

    void addPermissionsForRole(String domainId, String roleId, Set<String> permissionIds);

    void setPermissionsForRole(String domainId, String roleId, Set<String> permissionIds);

    Set<Permission> getPermissionsForRole(String domainId, String roleId);

    void removeAllPermissionsForRole(String domainId, String roleId);

}
