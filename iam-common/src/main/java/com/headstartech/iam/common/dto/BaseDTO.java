package com.headstartech.iam.common.dto;

import javax.validation.constraints.Size;

public class BaseDTO {

    @Size(max = 255, message = "Max length is 255 characters")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
