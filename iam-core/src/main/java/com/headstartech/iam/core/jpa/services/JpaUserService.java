package com.headstartech.iam.core.jpa.services;

import com.headstartech.iam.common.dto.Role;
import com.headstartech.iam.common.dto.User;
import com.headstartech.iam.common.exceptions.IAMBadRequestException;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.common.exceptions.IAMNotFoundException;
import com.headstartech.iam.core.annotations.TransactionalService;
import com.headstartech.iam.core.jpa.entities.DomainEntity;
import com.headstartech.iam.core.jpa.entities.RoleEntity;
import com.headstartech.iam.core.jpa.entities.UserEntity;
import com.headstartech.iam.core.jpa.repositories.JpaDomainRepository;
import com.headstartech.iam.core.jpa.repositories.JpaRoleRepository;
import com.headstartech.iam.core.jpa.repositories.JpaUserRepository;
import com.headstartech.iam.core.jpa.specifications.JpaUserSpecifications;
import com.headstartech.iam.core.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@TransactionalService
public class JpaUserService implements UserService {

    private final JpaDomainRepository domainRepo;
    private final JpaUserRepository userRepo;
    private final JpaRoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JpaUserService(JpaDomainRepository domainRepo, JpaUserRepository userRepo, JpaRoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.domainRepo = domainRepo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String createUser(String domainId, User user) throws IAMException {
        DomainEntity domainEntity = findDomain(domainId);

        final UserEntity userEntity = new UserEntity();
        userEntity.setId(StringUtils.isBlank(user.getId()) ? RandomString.randomId() : user.getId());
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setDomain(domainEntity);
        userEntity.setAttributes(user.getAttributes());
        return userRepo.save(userEntity).getId();
    }

    @Override
    public Page<User> getUsers(String domainId, Pageable page) {
        Page<UserEntity> userEntities = userRepo.findAll(JpaUserSpecifications.findUsersForDomain(domainId), page);
        return userEntities.map(UserEntity::getDTO);
    }

    @Override
    public User getUser(String domainId, String userId) throws IAMException {
        UserEntity userEntity = findUser(domainId, userId);
        return userEntity.getDTO();
    }

    @Override
    public void updateUser(String domainId, String userId, User user) throws IAMException {
        UserEntity userEntity = findUser(domainId, userId);
        if (!userId.equals(user.getId())) {
            throw new IAMBadRequestException("User id inconsistent with id passed in.");
        }

        userEntity.setUserName(user.getUserName());
        if(user.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userEntity.setAttributes(user.getAttributes());
        userRepo.save(userEntity);
    }

    @Override
    public void deleteUser(String domainId, String userId) throws IAMException {
        UserEntity userEntity = findUser(domainId, userId);
        userRepo.delete(userEntity);
    }

    @Override
    public void addRoles(String domainId, String userId, Set<String> roleIds) throws IAMException {
        UserEntity userEntity = findUser(domainId, userId);
        roleIds.stream().forEach(roleId -> userEntity.addRole(roleRepo.findOne(roleId)));
    }

    @Override
    public void setRoles(String domainId, String userId, Set<String> roleIds) throws IAMException {
        UserEntity userEntity = findUser(domainId, userId);
        userEntity.getRoles().clear();
        roleIds.stream().forEach(roleId -> userEntity.addRole(roleRepo.findOne(roleId)));
    }

    @Override
    public Set<Role> getRoles(String domainId, String userId) throws IAMException {
        UserEntity userEntity = findUser(domainId, userId);
        return userEntity.getRoles().stream().map(RoleEntity::getDTO).collect(Collectors.toSet());
    }

    @Override
    public void removeAllRoles(String domainId, String userId) throws IAMException {
        UserEntity userEntity = findUser(domainId, userId);
        userEntity.getRoles().clear();
    }

    private UserEntity findUser(final String domainId, final String userId) throws IAMException {
        final UserEntity userEntity = userRepo.findOne(userId);
        if (userEntity!= null) {
            if(!userEntity.getDomain().getId().equals(domainId)) {
                throw new IAMNotFoundException("No user with id " + userId + " exists.");
            }

            return userEntity;
        } else {
            throw new IAMNotFoundException("No user with id " + userId + " exists.");
        }
    }

    private DomainEntity findDomain(final String id) throws IAMException {
        final DomainEntity domainEntity= domainRepo.findOne(id);
        if (domainEntity!= null) {
            return domainEntity;
        } else {
            throw new IAMNotFoundException("No domain with id " + id + " exists.");
        }
    }
}
