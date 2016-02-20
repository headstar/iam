package com.headstartech.iam.core.jpa.repositories;

import com.headstartech.iam.core.jpa.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoleRepository  extends JpaRepository<RoleEntity, String>, JpaSpecificationExecutor {
}
