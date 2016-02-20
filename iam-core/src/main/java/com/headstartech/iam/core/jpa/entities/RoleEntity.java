package com.headstartech.iam.core.jpa.entities;

import com.headstartech.iam.common.dto.Role;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class RoleEntity extends BaseEntity {

    @Column(name = "name", length = 255)
    @Length(max = 255, message = "Max length in database is 255 characters")
    private String name;

    @ManyToOne
    private DomainEntity domain;

    public DomainEntity getDomain() {
        return domain;
    }

    public void setDomain(DomainEntity domain) {
        this.domain = domain;
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
