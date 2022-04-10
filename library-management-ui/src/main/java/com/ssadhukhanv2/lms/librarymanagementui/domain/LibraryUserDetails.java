package com.ssadhukhanv2.lms.librarymanagementui.domain;

import com.ssadhukhanv2.lms.librarymanagementui.entity.Role;
import com.ssadhukhanv2.lms.librarymanagementui.entity.User;
import com.ssadhukhanv2.lms.librarymanagementui.enumeration.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class LibraryUserDetails implements UserDetails {
    private final User user;
    private final Set<Role> roleSet;

    public LibraryUserDetails(User user, Set<Role> roleSet) {
        this.user = user;
        this.roleSet = roleSet;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (null == roleSet) {
            return Collections.emptySet();
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        roleSet.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getUserRole().getRoleCode())));
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.user.isUserAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.user.isUserAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.user.isUserCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isUserAccountEnabled();
    }
}
