package com.ssadhukhanv2.lms.librarymanagementui.repository;

import com.ssadhukhanv2.lms.librarymanagementui.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Double> {

    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<User> findByUserName(String userName);
}
