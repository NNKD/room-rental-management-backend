package com.roomrentalmanagementbackend.dto.apartment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentStatusDTO {
    int id;
    String name;
}
