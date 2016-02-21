package com.headstartech.iam.common.dto;

import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.Size;

@Relation(collectionRelation = "domains")
public class Domain extends BaseDTO {

    @Size(max = 255, message = "Max length is 255 characters")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
