package com.headstartech.iam.core.jpa.services;

import com.headstartech.iam.common.dto.User;
import com.headstartech.iam.common.exceptions.IAMException;
import com.headstartech.iam.common.exceptions.IAMNotFoundException;
import com.headstartech.iam.core.jpa.entities.DomainEntity;
import com.headstartech.iam.core.jpa.entities.UserEntity;
import com.headstartech.iam.core.jpa.repositories.JpaDomainRepository;
import com.headstartech.iam.core.jpa.repositories.JpaUserRepository;
import com.headstartech.iam.core.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(rollbackFor = { Exception.class })
public class JpaUserService implements UserService {

    private final JpaDomainRepository domainRepo;
    private final JpaUserRepository userRepo;

    @Autowired
    public JpaUserService(JpaDomainRepository domainRepo, JpaUserRepository userRepo) {
        this.domainRepo = domainRepo;
        this.userRepo = userRepo;
    }

    @Override
    public String createUser(String domainId, User user) throws IAMException {
        DomainEntity domainEntity = findDomain(domainId);

        final UserEntity userEntity = new UserEntity();
        userEntity.setId(StringUtils.isBlank(user.getId()) ? UUID.randomUUID().toString() : user.getId());
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(user.getPassword());
        userEntity.setDomain(domainEntity);
        return userRepo.save(userEntity).getId();
    }

    @Override
    public User getUser(String domainId, String userId) throws IAMException {
        UserEntity userEntity = findUser(userId);
        if(!userEntity.getDomain().getId().equals(domainId)) {
            throw new IAMNotFoundException("No user with id " + userId + " exists.");
        }
        return userEntity.getDTO();
    }

    private UserEntity findUser(final String id) throws IAMException {
        final UserEntity userEntity = userRepo.findOne(id);
        if (userEntity!= null) {
            return userEntity;
        } else {
            throw new IAMNotFoundException("No user with id " + id + " exists.");
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
