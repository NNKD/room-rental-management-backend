package com.roomrentalmanagementbackend.dto.apartment.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserApartmentDetailResponse {
    String rentalContractName;
    double rentalContractPrice;
    String rentalContractStatus;
    LocalDateTime rentalContractStartDate;
    LocalDateTime rentalContractEndDate;
    LocalDateTime rentalContractCreatedAt;
    String apartmentName;
    String apartmentType;
    int apartmentFloor;
    double apartmentWidth;
    double apartmentHeight;
    double apartmentBalcony;
    double apartmentTerrace;
    String userFullName;
    String userPhone;
    String userEmail;
}
