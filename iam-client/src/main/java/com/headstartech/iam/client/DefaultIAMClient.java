package com.headstartech.iam.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.headstartech.iam.common.dto.Domain;
import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.dto.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultIAMClient implements IAMClient {

    private static final ParameterizedTypeReference<DomainResource> domainResourceTypeReference = new ParameterizedTypeReference<DomainResource>() {};
    private static final ParameterizedTypeReference<UserResource> userResourceTypeReference = new ParameterizedTypeReference<UserResource>() {};
    private static final ParameterizedTypeReference<RoleResource> roleResourceTypeReference = new ParameterizedTypeReference<RoleResource>() {};
    private static final ParameterizedTypeReference<PermissionResource> permissionResourceTypeReference = new ParameterizedTypeReference<PermissionResource>() {};

    private final RestOperations restOperations;
    private final String baseRestURL;

    public DefaultIAMClient(RestOperations restOperations, String baseRestURL) {
        this.restOperations = restOperations;
        this.baseRestURL = baseRestURL;
    }

    @Override
    public Domain createDomain(Domain domain) {
        ResponseEntity<DomainResource> responseEntity = execute(createRequest(HttpMethod.POST, getDomainsBaseURL(), domain), domainResourceTypeReference);
        return responseEntity.getBody().getContent();
    }


    @Override
    public Domain getDomain(String domainId) {
        ResponseEntity<DomainResource> responseEntity = execute(createRequest(HttpMethod.GET, getDomainURL(domainId)), domainResourceTypeReference);
        return responseEntity.getBody().getContent();
    }

    @Override
    public Domain updateDomain(Domain domain) {
        ResponseEntity<DomainResource> responseEntity = execute(createRequest(HttpMethod.PUT, getDomainURL(domain.getId()), domain), domainResourceTypeReference);
        return responseEntity.getBody().getContent();
    }

    @Override
    public void deleteDomain(String id) {
        execute(createRequest(HttpMethod.DELETE, getDomainURL(id)));
    }

    @Override
    public Set<Domain> getDomains() {
        ResponseEntity<PagedDomainResources> responseEntity = execute(createRequest(HttpMethod.GET, getDomainsBaseURL()), new ParameterizedTypeReference<PagedDomainResources>() {});
        return responseEntity.getBody().getContent().stream().map(r -> r.getContent()).collect(Collectors.toSet());
    }

    @Override
    public User createUser(String domainId, User user) {
        ResponseEntity<UserResource> responseEntity = execute(createRequest(HttpMethod.POST, getUsersBaseURL(domainId), user), userResourceTypeReference);
        return responseEntity.getBody().getContent();
    }


    @Override
    public User getUser(String domainId, String userId) {
        ResponseEntity<UserResource> responseEntity = execute(createRequest(HttpMethod.GET, getUserURL(domainId, userId)), userResourceTypeReference);
        return responseEntity.getBody().getContent();
    }

    @Override
    public User updateUser(String domainId, User user) {
        ResponseEntity<UserResource> responseEntity = execute(createRequest(HttpMethod.PUT, getUserURL(domainId, user.getId()), user), userResourceTypeReference);
        return responseEntity.getBody().getContent();
    }

    @Override
    public void deleteUser(String domainId, String userId) {
        execute(createRequest(HttpMethod.DELETE, getUserURL(domainId, userId)));
    }

    @Override
    public Set<User> getUsers(String domainId) {
        ResponseEntity<PagedUsersResources> responseEntity = execute(createRequest(HttpMethod.GET, getUsersBaseURL(domainId)), new ParameterizedTypeReference<PagedUsersResources>() {});
        return responseEntity.getBody().getContent().stream().map(r -> r.getContent()).collect(Collectors.toSet());
    }

    @Override
    public Role createRole(String domainId, Role role) {
        ResponseEntity<RoleResource> responseEntity = execute(createRequest(HttpMethod.POST, getRolesBaseURL(domainId), role), roleResourceTypeReference);
        return responseEntity.getBody().getContent();
    }


    @Override
    public Role getRole(String domainId, String roleId) {
        ResponseEntity<RoleResource> responseEntity = execute(createRequest(HttpMethod.GET, getRoleURL(domainId, roleId)), roleResourceTypeReference);
        return responseEntity.getBody().getContent();
    }

    @Override
    public Role updateRole(String domainId, Role role) {
        ResponseEntity<RoleResource> responseEntity = execute(createRequest(HttpMethod.PUT, getRoleURL(domainId, role.getId()), role), roleResourceTypeReference);
        return responseEntity.getBody().getContent();
    }

    @Override
    public void deleteRole(String domainId, String roleId) {
        execute(createRequest(HttpMethod.DELETE, getRoleURL(domainId, roleId)));
    }

    @Override
    public Set<Role> getRoles(String domainId) {
        ResponseEntity<PagedRolesResources> responseEntity = execute(createRequest(HttpMethod.GET, getRolesBaseURL(domainId)), new ParameterizedTypeReference<PagedRolesResources>() {});
        return responseEntity.getBody().getContent().stream().map(r -> r.getContent()).collect(Collectors.toSet());
    }

    @Override
    public Permission createPermission(String domainId, Permission permission) {
        ResponseEntity<PermissionResource> responseEntity = execute(createRequest(HttpMethod.POST, getPermissionsBaseURL(domainId), permission), permissionResourceTypeReference);
        return responseEntity.getBody().getContent();
    }

    @Override
    public Permission getPermission(String domainId, String permissionId) {
        ResponseEntity<PermissionResource> responseEntity = execute(createRequest(HttpMethod.GET, getPermissionURL(domainId, permissionId)), permissionResourceTypeReference);
        return responseEntity.getBody().getContent();
    }

    @Override
    public Permission updatePermission(String domainId, Permission permissionId) {
        ResponseEntity<PermissionResource> responseEntity = execute(createRequest(HttpMethod.PUT, getPermissionURL(domainId, permissionId.getId()), permissionId), permissionResourceTypeReference);
        return responseEntity.getBody().getContent();
    }

    @Override
    public void deletePermission(String domainId, String permissionId) {
        execute(createRequest(HttpMethod.DELETE, getPermissionURL(domainId, permissionId)));
    }

    @Override
    public Set<Permission> getPermissions(String domainId) {
        ResponseEntity<PagedPermissionsResources> responseEntity = execute(createRequest(HttpMethod.GET, getPermissionsBaseURL(domainId)), new ParameterizedTypeReference<PagedPermissionsResources>() {});
        return responseEntity.getBody().getContent().stream().map(r -> r.getContent()).collect(Collectors.toSet());
    }

    @Override
    public void addPermissionsForRole(String domainId, String roleId, Set<String> permissionIds) {
        execute(createRequest(HttpMethod.POST, getRolePermissionsURL(domainId, roleId), permissionIds));
    }

    @Override
    public void setPermissionsForRole(String domainId, String roleId, Set<String> permissionIds) {
        execute(createRequest(HttpMethod.PUT, getRolePermissionsURL(domainId, roleId), permissionIds));
    }

    @Override
    public void removeAllPermissionsForRole(String domainId, String roleId) {
        execute(createRequest(HttpMethod.DELETE, getRolePermissionsURL(domainId, roleId)));
    }

    @Override
    public Set<Permission> getPermissionsForRole(String domainId, String roleId) {
        ResponseEntity<PermissionResources> responseEntity = execute(createRequest(HttpMethod.GET, getRolePermissionsURL(domainId, roleId)), new ParameterizedTypeReference<PermissionResources>() {});
        return new HashSet(responseEntity.getBody().getContent());
    }

    private RequestEntity<Void> createRequest(HttpMethod httpMethod, String uri) {
        return createRequest(httpMethod, uri, null);
    }

    private <T> RequestEntity<T> createRequest(HttpMethod httpMethod, String uri, T body) {
        RequestEntity.BodyBuilder builder = RequestEntity.method(httpMethod, toURI(uri));
        if(body != null) {
            builder.contentType(MediaType.APPLICATION_JSON);
        }
        if(!httpMethod.equals(HttpMethod.DELETE)) {
            builder.accept(MediaTypes.HAL_JSON);
        }
        return builder.body(body);
    }

    private <T> ResponseEntity<T> execute(RequestEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
        return restOperations.exchange(requestEntity, responseType);
    }

    private <T> ResponseEntity<Void> execute(RequestEntity<?> requestEntity) {
        return execute(requestEntity, new ParameterizedTypeReference<Void>() {});
    }

    private String getDomainsBaseURL() {
        return baseRestURL + "/domains";
    }

    private String getDomainURL(String domainId) {
        return String.format("%s/%s", getDomainsBaseURL(), domainId);
    }

    private String getUsersBaseURL(String domainId) {
        return String.format("%s/%s/users", getDomainsBaseURL(), domainId);
    }

    private String getUserURL(String domainId, String userId) {
        return String.format("%s/%s", getUsersBaseURL(domainId), userId);
    }

    private String getRolesBaseURL(String domainId) {
        return String.format("%s/%s/roles", getDomainsBaseURL(), domainId);
    }

    private String getRoleURL(String domainId, String roleId) {
        return String.format("%s/%s", getRolesBaseURL(domainId), roleId);
    }

    private String getPermissionsBaseURL(String domainId) {
        return String.format("%s/%s/permissions", getDomainsBaseURL(), domainId);
    }

    private String getRolePermissionsURL(String domainId, String roleId) {
        return String.format("%s/permissions", getRoleURL(domainId, roleId));
    }

    private String getPermissionURL(String domainId, String permissionId) {
        return String.format("%s/%s", getPermissionsBaseURL(domainId), permissionId);
    }

    private URI toURI(String s) {
        try {
            return new URI(s);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    static class DomainResource extends Resource<Domain> {
        @JsonCreator
        public DomainResource(Domain domain) {
            super(domain);
        }
    }

    static class UserResource extends Resource<User> {
        @JsonCreator
        public UserResource(User user) {
            super(user);
        }
    }

    static class RoleResource extends Resource<Role> {
        @JsonCreator
        public RoleResource(Role role) {
            super(role);
        }
    }

    static class PermissionResource extends Resource<Permission> {
        @JsonCreator
        public PermissionResource (Permission permission) {
            super(permission);
        }
    }

    static class PermissionResources extends Resources<Permission> {

    }

    static class PagedDomainResources extends PagedResources<DomainResource> {

    }

    static class PagedUsersResources extends PagedResources<UserResource> {

    }

    static class PagedRolesResources extends PagedResources<RoleResource> {

    }

    static class PagedPermissionsResources extends PagedResources<PermissionResource> {

    }
}
