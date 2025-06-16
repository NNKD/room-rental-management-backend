package com.roomrentalmanagementbackend.dto.billing.request;

import com.roomrentalmanagementbackend.dto.billing.response.ServiceDetailDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateBillRequest {
    private int rentalContractId;
    private List<ServiceDetailDTO> serviceDetails;
}
