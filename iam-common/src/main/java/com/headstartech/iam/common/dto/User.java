package com.headstartech.iam.common.dto;

import org.springframework.hateoas.core.Relation;

import java.util.Map;

@Relation(collectionRelation = "users")
public class User extends BaseDTO {

    private String userName;
    private String password;

    private Map<String, String> attributes;

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

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
