package com.headstartech.iam.core.jpa.repositories;

import com.headstartech.iam.core.jpa.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor {

    @Query("SELECT u FROM User u, Domain d WHERE u.domain = d AND d.id = :domainId and u.userName = :userName")
    UserEntity findByDomainAndUserName(@Param("domainId") String domainId, @Param("userName") String userName);
}

