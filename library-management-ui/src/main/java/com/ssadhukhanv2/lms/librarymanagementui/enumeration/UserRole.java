package com.ssadhukhanv2.lms.librarymanagementui.enumeration;


import java.util.stream.Stream;


public enum UserRole {
    ADMIN_USER("ADMIN_USER"), BUSINESS_USER("BUSINESS_USER");
    private String roleCode;

    UserRole(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public static UserRole of(String roleCode) {
        return Stream.of(UserRole.values()).filter(r -> r.getRoleCode().equals(roleCode)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
