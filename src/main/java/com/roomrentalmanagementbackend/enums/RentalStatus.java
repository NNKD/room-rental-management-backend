package com.roomrentalmanagementbackend.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum RentalStatus {
    ACTIVE("Đang có hiệu lực"), INACTIVE("Hết hiệu lực");
    String description;

    public static String getRentalStatus(String status) {
        return status.equals("ACTIVE") ? ACTIVE.getDescription() : INACTIVE.getDescription();
    }
}
