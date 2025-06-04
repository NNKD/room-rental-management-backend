package com.roomrentalmanagementbackend.dto.apartment.response;

import com.roomrentalmanagementbackend.enums.RentalStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentManagementResponse {
    int id;
    String name;
    String slug;
    double price;
    String type;
    String status;
    String user;
    String userEmail;
}
