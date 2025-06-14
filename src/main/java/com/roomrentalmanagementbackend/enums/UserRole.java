package com.roomrentalmanagementbackend.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum UserRole {
    ADMIN("admin"), USER("user");
    String description;

    public static String getUserRole(int role) {
        return role == 0 ? USER.getDescription() : ADMIN.getDescription();
    }
}
