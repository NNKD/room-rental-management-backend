package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.dto.mail.request.MailSenderRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailService {
    JavaMailSender mailSender;

    public void sendMail(MailSenderRequest request) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = null;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(request.getTo());
            mimeMessageHelper.setSubject(request.getSubject());
            mimeMessageHelper.setText(request.getBody(), false);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            log.error("Lỗi gửi mail đến {}: {}", request.getTo(), e.getMessage(), e);
            throw new MailSendException("Đã có lỗi khi gửi mail, vui lòng thử lại.");
        }
    }
}
