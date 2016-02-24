package com.headstartech.iam.core.jpa.entities;

import com.headstartech.iam.common.dto.Domain;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "domain")
public class DomainEntity extends BaseEntity {

    @Column(name = "description", length = 255)
    @Length(max = 255, message = "Max length in database is 255 characters")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="domain", orphanRemoval = true)
    private Set<UserEntity> users = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="domain", orphanRemoval = true)
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy="domain", orphanRemoval = true)
    private Set<PermissionEntity> permissions = new HashSet<>();


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Domain getDTO() {
        Domain domain = new Domain();
        domain.setId(getId());
        domain.setDescription(getDescription());
        return domain;
    }
}
