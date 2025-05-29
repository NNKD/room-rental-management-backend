package com.roomrentalmanagementbackend.dto.apartment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentInformationDTO {
    int id;
    double width;
    double height;
    int floor;
    double balcony;
    double terrace;
    String furniture;
    int bedrooms;
    int kitchens;
    int bathrooms;
}
