package com.roomrentalmanagementbackend.dto.user.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAccountResponse {
    String email;
    String username;
    String fullname;
    String phone;
}
