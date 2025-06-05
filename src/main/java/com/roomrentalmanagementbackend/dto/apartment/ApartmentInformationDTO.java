package com.roomrentalmanagementbackend.dto.apartment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentInformationDTO {
    int id;
    @Min(value = 1, message = "{apartment.detail.width.Min}")
    double width;
    @Min(value = 1, message = "{apartment.detail.height.Min}")
    double height;
    @Min(value = 0, message = "{apartment.detail.floor.Min}")
    @Max(value = 20, message = "{apartment.detail.floor.Max}")
    int floor;
    @Min(value = 0, message = "{apartment.detail.balcony.Min}")
    double balcony;
    @Min(value = 0, message = "{apartment.detail.terrace.Min}")
    double terrace;
    @NotBlank(message = "{apartment.detail.furniture.NotBlank}")
    String furniture;
    @Min(value = 1, message = "{apartment.detail.bedroom.Min}")
    int bedrooms;
    @Min(value = 1, message = "{apartment.detail.kitchen.Min}")
    int kitchens;
    @Min(value = 1, message = "{apartment.detail.bathroom.Min}")
    int bathrooms;
}
