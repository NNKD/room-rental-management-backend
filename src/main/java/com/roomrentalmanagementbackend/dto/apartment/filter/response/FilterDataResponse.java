package com.roomrentalmanagementbackend.dto.apartment.filter.response;

import com.roomrentalmanagementbackend.dto.apartment.response.ApartmentTypeResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterDataResponse {
    int minBedroom;
    int maxBedroom;
    List<ApartmentTypeResponse> types;
}
