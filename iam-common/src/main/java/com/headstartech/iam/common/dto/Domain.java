package com.headstartech.iam.common.dto;

import javax.validation.constraints.Size;

public class Domain {

    @Size(max = 255, message = "Max length is 255 characters")
    private String id;

    @Size(max = 5, message = "Max length is 255 characters")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
