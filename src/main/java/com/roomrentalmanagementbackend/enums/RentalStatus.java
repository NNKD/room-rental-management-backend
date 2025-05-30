package com.roomrentalmanagementbackend.enums;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum RentalStatus {
    ACTIVE("Đang có hiệu lực"),
    INACTIVE("Hết hiệu lực");

    String description;
}
