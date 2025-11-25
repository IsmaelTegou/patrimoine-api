package com.ktiservice.patrimoine.infrastructure.minio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Message object for RabbitMQ email queue.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailMessage implements Serializable {
    private String type;
    private String recipientEmail;
    private String subject;
    private String htmlContent;
}
