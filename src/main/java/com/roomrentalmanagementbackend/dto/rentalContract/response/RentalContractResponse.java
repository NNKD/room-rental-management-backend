package com.roomrentalmanagementbackend.dto.rentalContract.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentalContractResponse {
    int id;
    String name;
    String description;
    double price;
    String status;
    LocalDate startDate;
    LocalDate endDate;
    LocalDateTime createdAt;
    int userId;
    String fullname;
    String phone;
    String email;
    int apartmentId;
}