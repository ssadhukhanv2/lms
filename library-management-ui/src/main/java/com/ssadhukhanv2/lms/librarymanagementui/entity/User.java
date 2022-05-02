package com.ssadhukhanv2.lms.librarymanagementui.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "LMS_UI_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "USER_NAME", unique = true, nullable = false)
    private String userName;
    @Column(name = "EMAIL", unique = true, nullable = false)
    private String userEmail;
    @Column(name = "PASSWORD", nullable = false)
    private String userPassword;
    @Column(name = "ACCOUNT_EXPIRED")
    private boolean userAccountExpired;
    @Column(name = "ACCOUNT_LOCKED")
    private boolean userAccountLocked;
    @Column(name = "ACCOUNT_CREDENTIALS_EXPIRED")
    private boolean userCredentialsExpired;
    @Column(name = "ACCOUNT_ENABLED")
    private boolean userAccountEnabled;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "LMS_USER_ROLE_MAPPING",
            joinColumns = {@JoinColumn(name = "FK_USER_ID",table = "LMS_UI_USER")},
            inverseJoinColumns = {@JoinColumn(name = "FK_ROLE_ID", table = "LMS_UI_ROLE")})
    private Set<Role> roleSet = new HashSet<Role>();

    public void addRole(Role role) {
        this.roleSet.add(role);
    }

    public void removeRole(Role role) {
        this.roleSet.remove(role);
    }
}
