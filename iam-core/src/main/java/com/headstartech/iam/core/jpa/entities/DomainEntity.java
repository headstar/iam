package com.headstartech.iam.core.jpa.entities;

import com.headstartech.iam.common.dto.Domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DomainEntity extends BaseEntity {

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


    public Domain getDTO() {
        Domain domain = new Domain();
        domain.setId(getId());
        return domain;
    }
}
