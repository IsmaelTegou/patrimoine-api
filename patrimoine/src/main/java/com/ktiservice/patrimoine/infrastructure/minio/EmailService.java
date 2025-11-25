package com.ktiservice.patrimoine.infrastructure.minio;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Email service using SendGrid via RabbitMQ for async processing.
 * Sends emails for user registration, password reset, and notifications.
 */
@Slf4j
@Service
public class EmailService {

    private final RabbitTemplate rabbitTemplate;
    private final SendGrid sendGrid;
    private final String senderEmail;

    public EmailService(RabbitTemplate rabbitTemplate,
                        SendGrid sendGrid,
                        @Value("${app.email.sender:noreply@heritage-cameroon.com}") String senderEmail) {
        this.rabbitTemplate = rabbitTemplate;
        this.sendGrid = sendGrid;
        this.senderEmail = senderEmail;
    }

    /**
     * Send confirmation email asynchronously via RabbitMQ.
     */
    public void sendConfirmationEmail(String recipientEmail, String confirmationLink) {
        log.info("Queuing confirmation email for: {}", recipientEmail);

        EmailMessage message = EmailMessage.builder()
                .type("CONFIRMATION")
                .recipientEmail(recipientEmail)
                .subject("Confirm Your Heritage Account")
                .htmlContent(buildConfirmationEmailHtml(confirmationLink))
                .build();

        rabbitTemplate.convertAndSend("events-exchange", "email.confirmation", message);
    }

    /**
     * Send password reset email asynchronously via RabbitMQ.
     */
    public void sendPasswordResetEmail(String recipientEmail, String resetLink) {
        log.info("Queuing password reset email for: {}", recipientEmail);

        EmailMessage message = EmailMessage.builder()
                .type("PASSWORD_RESET")
                .recipientEmail(recipientEmail)
                .subject("Reset Your Password")
                .htmlContent(buildPasswordResetEmailHtml(resetLink))
                .build();

        rabbitTemplate.convertAndSend("events-exchange", "email.reset", message);
    }

    /**
     * Send review notification email asynchronously.
     */
    public void sendReviewNotificationEmail(String recipientEmail, String siteName, String reviewComment) {
        log.info("Queuing review notification email for: {}", recipientEmail);

        EmailMessage message = EmailMessage.builder()
                .type("REVIEW_NOTIFICATION")
                .recipientEmail(recipientEmail)
                .subject("New Review on " + siteName)
                .htmlContent(buildReviewNotificationEmailHtml(siteName, reviewComment))
                .build();

        rabbitTemplate.convertAndSend("events-exchange", "email.notification", message);
    }


    /**
     * Send email directly (synchronously) - for critical emails only.
     */
    public void sendEmailDirect(String recipientEmail, String subject, String htmlContent) {
        try {
            Email from = new Email(senderEmail);
            Email to = new Email(recipientEmail);
            Content content = new Content("text/html", htmlContent);
            Mail mail = new Mail(from, subject, to, content);

            // Construire correctement la requête SendGrid
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            log.info("Email sent directly to: {} | Status: {}",
                    recipientEmail, response.getStatusCode());

        } catch (IOException ex) {
            log.error("Failed to send email to: {}", recipientEmail, ex);
        }
    }


    /**
     * Build confirmation email HTML template.
     */
    private String buildConfirmationEmailHtml(String confirmationLink) {
        return """
                <html>
                    <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                        <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                            <h2>Welcome to Heritage Cameroon!</h2>
                            <p>Thank you for registering. Please confirm your email address by clicking the link below:</p>
                            <p>
                                <a href="%s" style="background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">
                                    Confirm Email
                                </a>
                            </p>
                            <p>If you did not register, please ignore this email.</p>
                            <hr>
                            <p style="font-size: 12px; color: #666;">
                                This is an automated email. Please do not reply directly.
                            </p>
                        </div>
                    </body>
                </html>
                """.formatted(confirmationLink);
    }

    /**
     * Build password reset email HTML template.
     */
    private String buildPasswordResetEmailHtml(String resetLink) {
        return """
                <html>
                    <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                        <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                            <h2>Reset Your Password</h2>
                            <p>We received a request to reset your password. Click the link below to proceed:</p>
                            <p>
                                <a href="%s" style="background-color: #dc3545; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">
                                    Reset Password
                                </a>
                            </p>
                            <p>This link will expire in 24 hours.</p>
                            <p>If you did not request this, please ignore this email.</p>
                            <hr>
                            <p style="font-size: 12px; color: #666;">
                                This is an automated email. Please do not reply directly.
                            </p>
                        </div>
                    </body>
                </html>
                """.formatted(resetLink);
    }

    /**
     * Build review notification email HTML template.
     */
    private String buildReviewNotificationEmailHtml(String siteName, String reviewComment) {
        return """
                <html>
                    <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                        <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                            <h2>New Review on %s</h2>
                            <p>A visitor has left a new review on your heritage site:</p>
                            <div style="background-color: #f8f9fa; padding: 15px; border-left: 4px solid #007bff; margin: 20px 0;">
                                <p><strong>Comment:</strong></p>
                                <p>%s</p>
                            </div>
                            <p>
                                <a href="https://heritage-cameroon.com/admin/reviews" style="background-color: #28a745; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">
                                    View & Moderate
                                </a>
                            </p>
                            <hr>
                            <p style="font-size: 12px; color: #666;">
                                This is an automated email. Please do not reply directly.
                            </p>
                        </div>
                    </body>
                </html>
                """.formatted(siteName, reviewComment);
    }
}