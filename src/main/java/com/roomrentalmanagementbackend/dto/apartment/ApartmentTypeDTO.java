package com.roomrentalmanagementbackend.dto.apartment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentTypeDTO {
    int id;
    @NotBlank(message = "{apartment.type.name.NotBlank}")
    String name;
    @NotBlank(message = "{apartment.type.description.NotBlank}")
    String description;
}
