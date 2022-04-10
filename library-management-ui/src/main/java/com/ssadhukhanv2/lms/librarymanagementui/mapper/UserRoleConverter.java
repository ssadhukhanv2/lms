package com.ssadhukhanv2.lms.librarymanagementui.mapper;

import com.ssadhukhanv2.lms.librarymanagementui.enumeration.UserRole;
import org.apache.commons.lang3.EnumUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


//Persisting Enum with JPA: https://www.baeldung.com/jpa-persisting-enums-in-jpa
//JPA Attribute Converter: https://www.baeldung.com/jpa-attribute-converters

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {
    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        if (null != userRole) {
            return userRole.getRoleCode();
        }
        return null;
    }

    @Override
    public UserRole convertToEntityAttribute(String roleCode) {
        if (roleCode != null && EnumUtils.isValidEnum(UserRole.class, roleCode)) {
            return UserRole.of(roleCode);
        }
        return null;
    }
}
