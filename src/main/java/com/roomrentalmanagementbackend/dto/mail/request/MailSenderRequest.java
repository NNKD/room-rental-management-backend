package com.roomrentalmanagementbackend.dto.mail.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailSenderRequest {
    String to;
    String subject;
    String body;
}
