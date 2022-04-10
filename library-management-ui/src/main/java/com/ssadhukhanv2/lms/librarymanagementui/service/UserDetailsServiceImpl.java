package com.ssadhukhanv2.lms.librarymanagementui.service;

import com.ssadhukhanv2.lms.librarymanagementui.domain.LibraryUserDetails;
import com.ssadhukhanv2.lms.librarymanagementui.entity.User;
import com.ssadhukhanv2.lms.librarymanagementui.exception.LibraryManagementException;
import com.ssadhukhanv2.lms.librarymanagementui.repository.RoleRepository;
import com.ssadhukhanv2.lms.librarymanagementui.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Cannot find username " + username));
        return new LibraryUserDetails(user, user.getRoleSet());
    }
}
