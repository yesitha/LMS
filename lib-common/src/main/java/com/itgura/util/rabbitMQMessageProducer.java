package com.itgura.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class rabbitMQMessageProducer {
    @Qualifier("ampqTemplate")
    private final AmqpTemplate amqpTemplate;

    public void publish(Object payload, String exchange, String routingKey) {
        log.info("Publishing message to RabbitMQ exchange: {} with routing key: {}. Payload :{}", exchange, routingKey, payload);
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
        log.info("Published message to RabbitMQ exchange: {} with routing key: {}. Payload :{}", exchange, routingKey, payload);
    }
}