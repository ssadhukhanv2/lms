package com.ssadhukhanv2.lms.librarymanagementui.entity;


import com.ssadhukhanv2.lms.librarymanagementui.enumeration.UserRole;
import lombok.Data;

import javax.persistence.*;

//Persisting Enum with JPA: https://www.baeldung.com/jpa-persisting-enums-in-jpa

@Data
@Entity(name = "LMS_UI_ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_ID")
    private long id;
    @Column(name = "ROLE_CODE",nullable = false)
    private UserRole userRole;

}
