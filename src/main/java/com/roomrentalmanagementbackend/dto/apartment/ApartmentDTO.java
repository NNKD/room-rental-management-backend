package com.roomrentalmanagementbackend.dto.apartment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "{apartment.dto.name.NotBlank}")
    String name;
    @NotBlank(message = "{apartment.dto.slug.NotBlank}")
    String slug;
    @NotBlank(message = "{apartment.dto.brief.NotBlank}")
    String brief;
    @NotBlank(message = "{apartment.dto.description.NotBlank}")
    String description;
    @Min(value = 0, message = "{apartment.dto.hot.Min}")
    @Max(value = 1, message = "{apartment.dto.hot.Max}")
    int hot;
    @Min(value = 0, message = "{apartment.dto.price.Min}")
    double price;
    @NotNull(message = "{apartment.dto.type.NotNull}")
    ApartmentTypeDTO type;
    @NotNull(message = "{apartment.dto.status.NotNull}")
    ApartmentStatusDTO status;
    @NotNull(message = "{apartment.dto.discount.NotNull}")
    List<ApartmentDiscountDTO> discounts;
    @NotNull(message = "{apartment.dto.image.NotNull}")
    @Size(min = 1, message = "{apartment.image.Size}")
    List<ApartmentImageDTO> images;
    @NotNull(message = "{apartment.dto.information.NotNull}")
    @Valid
    ApartmentInformationDTO information;

}
