package com.headstartech.iam.core.jpa.entities;

import com.headstartech.iam.common.dto.Domain;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DomainEntity extends BaseEntity {

    @Column(name = "description", length = 255)
    @Length(max = 255, message = "Max length in database is 255 characters")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private Set<UserEntity> users = new HashSet<>();

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users.clear();
        if (users != null) {
            this.users.addAll(users);
        }
    }

    public void addUser(UserEntity user) {
        if (user != null) {
            this.users.add(user);
        }
    }

    public void removeUser(UserEntity user) {
        this.users.remove(user);
    }


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
