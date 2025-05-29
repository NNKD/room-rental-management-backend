package com.roomrentalmanagementbackend.dto.apartment;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentDTO {
    int id;
    String name;
    String slug;
    String brief;
    String description;
    int hot;
    double price;
    ApartmentTypeDTO type;
    ApartmentStatusDTO status;
    List<ApartmentDiscountDTO> discounts;
    List<ApartmentImageDTO> images;
    ApartmentInformationDTO information;

}
