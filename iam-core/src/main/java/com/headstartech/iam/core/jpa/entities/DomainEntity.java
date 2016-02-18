package com.headstartech.iam.core.jpa.entities;

import com.headstartech.iam.common.dto.Domain;

import javax.persistence.Entity;

@Entity
public class DomainEntity extends BaseEntity {

    public Domain getDTO() {
        Domain domain = new Domain();
        domain.setId(getId());
        return domain;
    }
}
