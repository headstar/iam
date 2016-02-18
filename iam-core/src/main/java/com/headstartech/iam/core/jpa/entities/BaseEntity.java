package com.headstartech.iam.core.jpa.entities;


import com.headstartech.iam.common.exceptions.IAMPreconditionException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class BaseEntity {

    @Id
    @Column(name = "id", updatable = false, length = 255)
    @Length(max = 255, message = "Max length in database is 255 characters")
    private String id;

    @Version
    @Column(name = "entity_version", nullable = false)
    private Long entityVersion;


    public void setId(final String id) throws IAMPreconditionException {
        if (StringUtils.isBlank(this.id)) {
            this.id = id;
        } else {
            throw new IAMPreconditionException("Id already set for this entity.");
        }
    }

    public String getId() {
        return id;
    }

    public Long getEntityVersion() {
        return entityVersion;
    }

    protected void setEntityVersion(final Long entityVersion) {
        this.entityVersion = entityVersion;
    }
}
