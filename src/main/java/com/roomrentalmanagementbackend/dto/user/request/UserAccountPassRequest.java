package com.roomrentalmanagementbackend.dto.user.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAccountPassRequest {
    String pass;
    String newPass;
}
