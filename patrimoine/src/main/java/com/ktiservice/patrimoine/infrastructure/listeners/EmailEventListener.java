package com.ktiservice.patrimoine.infrastructure.listeners;

import com.ktiservice.patrimoine.infrastructure.minio.EmailMessage;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


import java.io.IOException;

/**
 * RabbitMQ listener for email events.
 * Processes email messages from the queue asynchronously.
 */
@Slf4j
@Service
public class EmailEventListener {

    private final SendGrid sendGrid;
    private final String senderEmail;

    public EmailEventListener(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
        this.senderEmail = "noreply@heritage-cameroon.com";
    }

    /**
     * Listen to email queue and send emails.
     */
    @RabbitListener(queues = "email-queue")
    public void handleEmailEvent(EmailMessage message) {
        log.info("Processing email event: {} to {}", message.getType(), message.getRecipientEmail());

        try {
            Email from = new Email(senderEmail);
            Email to = new Email(message.getRecipientEmail());
            Content content = new Content("text/html", message.getHtmlContent());
            Mail mail = new Mail(from, message.getSubject(), to, content);

            // Création de la requête SendGrid correctement
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            log.info("Email sent successfully to: {} | Status Code: {}",
                    message.getRecipientEmail(), response.getStatusCode());
        } catch (IOException ex) {
            log.error("Failed to send email to: {}", message.getRecipientEmail(), ex);
        }
    }
}
