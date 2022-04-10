package com.ssadhukhanv2.lms.librarymanagementui.repository;

import com.ssadhukhanv2.lms.librarymanagementui.entity.Role;
import com.ssadhukhanv2.lms.librarymanagementui.enumeration.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
    public Optional<Role> findByUserRole(UserRole userRole);
}
