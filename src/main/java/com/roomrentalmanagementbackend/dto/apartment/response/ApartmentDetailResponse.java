package com.roomrentalmanagementbackend.dto.apartment.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentDetailResponse {
    String name;
    String slug;
    String brief;
    String description;
    double price;
    double width;
    double height;
    int floor;
    double balcony;
    double terrace;
    String furniture;
    int bedrooms;
    int kitchens;
    int bathrooms;
    String type;
    List<ApartmentDiscountResponse> discounts;
    List<ApartmentImageResponse> images;

}
