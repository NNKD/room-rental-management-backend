package com.roomrentalmanagementbackend.dto.billing.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BillResponseDTO {
    private int id;
    private String name;
    private double rentalAmount;
    private List<ServiceDetailDTO> serviceDetails;
    private double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;
    private String status;
}

