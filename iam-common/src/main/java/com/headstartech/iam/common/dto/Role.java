package com.headstartech.iam.common.dto;

import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "roles")
public class Role extends BaseDTO {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
