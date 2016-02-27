package com.headstartech.iam.core.jpa.specifications;

import com.headstartech.iam.core.jpa.entities.DomainEntity;
import com.headstartech.iam.core.jpa.entities.DomainEntity_;
import com.headstartech.iam.core.jpa.entities.RoleEntity;
import com.headstartech.iam.core.jpa.entities.RoleEntity_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

public class JpaRoleSpecifications {

    public static Specification<RoleEntity> findRolesForDomain(String domainId) {
        return (final Root<RoleEntity> root, final CriteriaQuery<?> cq, final CriteriaBuilder cb) -> {
            Join<RoleEntity, DomainEntity> domain = root.join(RoleEntity_.domain);
            return cb.and(cb.equal(domain.get(DomainEntity_.id), domainId));
        };
    }
}
