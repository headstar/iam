package com.headstartech.iam.core.jpa.entities;

import com.headstartech.iam.common.dto.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UserEntity extends BaseEntity {

    @Column(name = "user_name", length = 255)
    private String userName;

    @Column(name = "password", length = 255)
    private String password;

    @ManyToOne
    private DomainEntity domain;

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

    public User getDTO() {
        User user = new User();
        user.setId(getId());
        user.setUserName(getUserName());
        user.setPassword(getPassword());
        return user;
    }
}
