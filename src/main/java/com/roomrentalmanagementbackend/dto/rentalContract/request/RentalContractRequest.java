package com.roomrentalmanagementbackend.dto.rentalContract.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentalContractRequest {
    String name;
    String description;
    double price;
    String status;
    LocalDateTime startDate;
    LocalDateTime endDate;
    int userId;
    int apartmentId; // Nếu cần liên kết với căn hộ
}