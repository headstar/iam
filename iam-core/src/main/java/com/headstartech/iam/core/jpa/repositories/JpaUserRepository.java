package com.headstartech.iam.core.jpa.repositories;

import com.headstartech.iam.core.jpa.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor {

    UserEntity findByUserName(String userName);
}

