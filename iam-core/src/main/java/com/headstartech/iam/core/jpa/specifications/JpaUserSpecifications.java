package com.headstartech.iam.core.jpa.specifications;

import com.headstartech.iam.core.jpa.entities.DomainEntity;
import com.headstartech.iam.core.jpa.entities.DomainEntity_;
import com.headstartech.iam.core.jpa.entities.UserEntity;
import com.headstartech.iam.core.jpa.entities.UserEntity_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

public class JpaUserSpecifications {

    public static Specification<UserEntity> findUsersForDomain(String domainId) {
        return (final Root<UserEntity> root, final CriteriaQuery<?> cq, final CriteriaBuilder cb) -> {
            Join<UserEntity, DomainEntity> domain = root.join(UserEntity_.domain);
            return cb.and(cb.equal(domain.get(DomainEntity_.id), domainId));
        };
    }
}
