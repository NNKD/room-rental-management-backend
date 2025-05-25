package com.roomrentalmanagementbackend.dto.apartment.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentDiscountResponse {
    double discount_percent;
    int duration_month;
}
