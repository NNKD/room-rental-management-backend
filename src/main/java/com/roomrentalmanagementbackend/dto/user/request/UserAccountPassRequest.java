package com.roomrentalmanagementbackend.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAccountPassRequest {
    @NotBlank(message = "Không được để trống")
    String pass;
    @NotBlank(message = "Không được để trống")
    String newPass;
}
