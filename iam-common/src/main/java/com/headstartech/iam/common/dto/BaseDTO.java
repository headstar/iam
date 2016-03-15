package com.headstartech.iam.common.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.Size;
import java.util.Date;

public class BaseDTO {

    @Size(max = 255, message = "Max length is 255 characters")
    private String id;

    private Date created;
    private Date updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (final JsonProcessingException ioe) {
            return ioe.getLocalizedMessage();
        }
    }

}
