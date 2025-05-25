package com.roomrentalmanagementbackend.dto.apartment.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentDetailFormRequest {
    String slug;
    @NotBlank(message = "{apartment.name.required}")
    String name;
    @NotBlank(message = "{apartment.email.required}")
    @Email(message = "{apartment.email.invalid}")
    String email;
    @NotBlank(message = "{apartment.message.required}")
    String message;
}
