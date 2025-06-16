package com.roomrentalmanagementbackend.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAccountUsernameRequest {
    @NotBlank(message = "Không được để trống")
    String usernameOld;
    @NotBlank(message = "Không được để trống")
    String newUsername;
}
