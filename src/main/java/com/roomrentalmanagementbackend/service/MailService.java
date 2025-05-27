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
    public void sendPasswordResetMail(String to, String newPassword) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Đặt lại mật khẩu - Room Rental Management");

            // Định dạng email HTML đẹp mắt
            String htmlContent = """
                <html>
                <body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>
                    <div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 10px;'>
                        <h2 style='color: #2c3e50; text-align: center;'>Đặt lại mật khẩu</h2>
                        <p>Xin chào,</p>
                        <p>Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn. Dưới đây là mật khẩu mới được tạo tự động:</p>
                        <div style='background-color: #f8f9fa; padding: 15px; text-align: center; border-radius: 5px;'>
                            <h3 style='color: #e74c3c; margin: 0;'>%s</h3>
                        </div>
                        <p>Vui lòng đăng nhập bằng mật khẩu này và đổi mật khẩu mới trong phần cài đặt tài khoản để đảm bảo an toàn.</p>
                        <p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng liên hệ chúng tôi ngay lập tức qua email: <a href='mailto:support@roomrental.com'>support@roomrental.com</a>.</p>
                        <p style='text-align: center; color: #888; font-size: 12px;'>© 2025 Room Rental Management. All rights reserved.</p>
                    </div>
                </body>
                </html>
                """.formatted(newPassword);

            mimeMessageHelper.setText(htmlContent, true); // true để bật HTML
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Lỗi gửi email đặt lại mật khẩu đến {}: {}", to, e.getMessage(), e);
            throw new MailSendException("Đã có lỗi khi gửi email đặt lại mật khẩu, vui lòng thử lại.");
        }
    }
}
