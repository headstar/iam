package com.headstartech.iam.core.jpa.entities;

import com.headstartech.iam.common.dto.User;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity(name = "User")
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Column(name = "user_name", length = 255)
    private String userName;

    @Column(name = "password", length = 255)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<RoleEntity> roles;

    @ManyToOne
    private DomainEntity domain;

    @ElementCollection
    private Map<String, String> attributes = new HashMap<>();

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public DomainEntity getDomain() {
        return domain;
    }

    public void setDomain(DomainEntity domain) {
        this.domain = domain;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public void addRole(RoleEntity role) {
        getRoles().add(role);
    }

    public void removeRole(RoleEntity role) {
        getRoles().remove(role);
    }

    public User getDTO() {
        User user = new User();
        user.setId(getId());
        user.setUserName(getUserName());
        user.setPassword(null);  // never return the (encrypted) password
        user.setAttributes(getAttributes());
        user.setCreated(getCreated());
        user.setUpdated(getUpdated());
        return user;
    }
}
