package dev.lumah.orders_ms.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.DefaultJacksonJavaTypeMapper;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String ORDER_PAYMENT_PENDING = "order.payment.pending";

    @Bean
    public JacksonJsonMessageConverter messageConverter() {
        JacksonJsonMessageConverter converter = new JacksonJsonMessageConverter();

        DefaultJacksonJavaTypeMapper typeMapper = new DefaultJacksonJavaTypeMapper();
        typeMapper.setTrustedPackages("dev.lumah.orders_ms.dto");

        converter.setJavaTypeMapper(typeMapper);

        return converter;
    }

    @Bean
    Queue orderPaymentPending() {
        return QueueBuilder
                .durable(ORDER_PAYMENT_PENDING)
                .build();
    }
}
