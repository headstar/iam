package com.headstartech.iam.core.jpa.entities;

import com.headstartech.iam.common.dto.Permission;
import com.headstartech.iam.common.dto.Role;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

@Entity
public class PermissionEntity extends BaseEntity {

    @Column(name = "name", length = 255)
    @Length(max = 255, message = "Max length in database is 255 characters")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    private Set<RoleEntity> roles;

    @ManyToOne
    private DomainEntity domain;

    public DomainEntity getDomain() {
        return domain;
    }

    public void setDomain(DomainEntity domain) {
        this.domain = domain;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Permission getDTO() {
        Permission permission = new Permission();
        permission.setId(getId());
        permission.setName(getName());
        return permission;
    }
}
