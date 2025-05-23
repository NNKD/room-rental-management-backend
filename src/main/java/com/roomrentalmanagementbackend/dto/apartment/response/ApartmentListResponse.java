package com.roomrentalmanagementbackend.dto.apartment.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentListResponse {
    String name;
    String slug;
    String brief;
    double price;
    List<ApartmentImageResponse> images;
}
