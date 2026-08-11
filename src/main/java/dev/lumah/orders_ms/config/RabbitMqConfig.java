package dev.lumah.orders_ms.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public Queue createQueue() {
        return new Queue("");
    }
}
