package com.headstartech.iam.core.jpa.specifications;

import com.headstartech.iam.core.jpa.entities.DomainEntity;
import com.headstartech.iam.core.jpa.entities.DomainEntity_;
import com.headstartech.iam.core.jpa.entities.PermissionEntity;
import com.headstartech.iam.core.jpa.entities.PermissionEntity_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

public class JpaPermissionSpecifications {

    public static Specification<PermissionEntity> findPermissionsForDomain(String domainId) {
        return (final Root<PermissionEntity> root, final CriteriaQuery<?> cq, final CriteriaBuilder cb) -> {
            Join<PermissionEntity, DomainEntity> domain = root.join(PermissionEntity_.domain);
            return cb.and(cb.equal(domain.get(DomainEntity_.id), domainId));
        };
    }
}
