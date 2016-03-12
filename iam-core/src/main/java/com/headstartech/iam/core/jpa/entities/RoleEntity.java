package com.headstartech.iam.core.jpa.entities;

import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.common.dto.Role;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Role")
@Table(name = "role")
public class RoleEntity extends BaseEntity {

    @Column(name = "name", length = 255)
    @Length(max = 255, message = "Max length in database is 255 characters")
    private String name;

    @ManyToOne
    private DomainEntity domain;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<PermissionEntity> permissions;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<UserEntity> users;

    public DomainEntity getDomain() {
        return domain;
    }

    public void setDomain(DomainEntity domain) {
        this.domain = domain;
    }

    public void addPermission(PermissionEntity permission) {
        getPermissions().add(permission);
    }

    public Set<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionEntity> permissions) {
        this.permissions = permissions;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getDTO() {
        Role role = new Role();
        role.setId(getId());
        role.setName(getName());
        return role;
    }
}
