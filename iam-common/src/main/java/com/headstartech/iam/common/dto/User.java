package com.headstartech.iam.common.dto;

import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "users")
public class User extends BaseDTO {

    private String userName;
    private String password;

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
}
