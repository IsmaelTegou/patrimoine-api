package com.ktiservice.patrimoine.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * RabbitMQ configuration for asynchronous message processing.
 * Defines queues, exchanges, and bindings for email, reports, and audit events.
 */
@Configuration
public class RabbitMQConfig {

    // Queue names
    public static final String EMAIL_QUEUE = "email-queue";
    public static final String REPORT_QUEUE = "report-queue";
    public static final String AUDIT_QUEUE = "audit-queue";

    // Exchange names
    public static final String EVENTS_EXCHANGE = "events-exchange";

    // Routing keys
    public static final String EMAIL_ROUTING_KEY = "email.*";
    public static final String REPORT_ROUTING_KEY = "report.*";
    public static final String AUDIT_ROUTING_KEY = "audit.*";

    /**
     * Email queue for sending confirmation, password reset, and notification emails.
     */
    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true, false, false);
    }

    /**
     * Report queue for generating PDF/Excel reports asynchronously.
     */
    @Bean
    public Queue reportQueue() {
        return new Queue(REPORT_QUEUE, true, false, false);
    }

    /**
     * Audit queue for logging all critical actions.
     */
    @Bean
    public Queue auditQueue() {
        return new Queue(AUDIT_QUEUE, true, false, false);
    }

    /**
     * Topic exchange for routing events to appropriate queues.
     */
    @Bean
    public TopicExchange eventsExchange() {
        return new TopicExchange(EVENTS_EXCHANGE, true, false);
    }

    /**
     * Binding for email queue.
     */
    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange eventsExchange) {
        return BindingBuilder.bind(emailQueue)
                .to(eventsExchange)
                .with(EMAIL_ROUTING_KEY);
    }

    /**
     * Binding for report queue.
     */
    @Bean
    public Binding reportBinding(Queue reportQueue, TopicExchange eventsExchange) {
        return BindingBuilder.bind(reportQueue)
                .to(eventsExchange)
                .with(REPORT_ROUTING_KEY);
    }

    /**
     * Binding for audit queue.
     */
    @Bean
    public Binding auditBinding(Queue auditQueue, TopicExchange eventsExchange) {
        return BindingBuilder.bind(auditQueue)
                .to(eventsExchange)
                .with(AUDIT_ROUTING_KEY);
    }

    /**
     * Message converter for JSON serialization.
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

