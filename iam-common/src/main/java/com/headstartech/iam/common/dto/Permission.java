package com.headstartech.iam.common.dto;

import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Relation(collectionRelation = "permissions")
public class Permission extends BaseDTO {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
