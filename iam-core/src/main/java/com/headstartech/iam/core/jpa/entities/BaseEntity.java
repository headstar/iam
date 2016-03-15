package com.headstartech.iam.core.jpa.entities;


import com.headstartech.iam.common.exceptions.IAMPreconditionException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class BaseEntity {

    @Id
    @Column(name = "id", updatable = false, length = 255)
    @Length(max = 255, message = "Max length in database is 255 characters")
    private String id;

    @Version
    @Column(name = "entity_version", nullable = false)
    private Long entityVersion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    private Date updated;

    @PrePersist
    protected void onCreateBaseEntity() {
        final Date date = new Date();
        this.updated = date;
        this.created = date;
    }

    @PreUpdate
    protected void onUpdateBaseEntity() {
        this.updated = new Date();
    }

    public Date getCreated() {
        return created;
    }

    protected void setCreated(Date created) {
        this.created = new Date(created.getTime());
    }

    public Date getUpdated() {
        return updated;
    }

    protected void setUpdated(Date updated) {
        this.updated = new Date(updated.getTime());
    }

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
