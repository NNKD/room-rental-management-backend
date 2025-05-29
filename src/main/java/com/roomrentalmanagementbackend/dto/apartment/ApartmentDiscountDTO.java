package com.roomrentalmanagementbackend.dto.apartment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentDiscountDTO {
    int id;
    double discount_percent;
    int duration_month;
}
