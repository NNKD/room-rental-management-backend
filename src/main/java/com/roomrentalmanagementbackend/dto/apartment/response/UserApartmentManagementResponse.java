package com.roomrentalmanagementbackend.dto.apartment.response;

import com.roomrentalmanagementbackend.dto.rentalContract.response.RentalContractResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserApartmentManagementResponse {
    String name;
    String slug;
    String type;
    LocalDateTime startDate;
    LocalDateTime endDate;
}
