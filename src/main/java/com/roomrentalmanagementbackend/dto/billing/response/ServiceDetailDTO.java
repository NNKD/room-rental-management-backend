package com.roomrentalmanagementbackend.dto.billing.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceDetailDTO {
    private String name;
    private int quantity;
    private double price;
    private double totalPrice;
    private int rentalContractId;
    private int serviceId;
}
