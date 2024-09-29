package com.itgura.service.config;




import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class youtubePermissionManagerConfig {
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queue.permissionGrant}")
    private String permissionGrantQueue;

    @Value("${rabbitmq.queue.permissionRevoke}")
    private String permissionRevokeQueue;

    @Value("${rabbitmq.routing-keys.permissionGrant}")
    private String permissionGrantRoutingKey;

    @Value("${rabbitmq.routing-keys.permissionRevoke}")
    private String permissionRevokeRoutingKey;


    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue permissionGrantQueue() {
        return new Queue(this.permissionGrantQueue);
    }

  @Bean
    public Queue permissionRevokeQueue() {
        return new Queue(this.permissionRevokeQueue);
    }

    @Bean
    public Binding permissionGrantBinding() {
        return BindingBuilder
                .bind(permissionGrantQueue())
                .to(internalTopicExchange())
                .with(permissionGrantRoutingKey);
    }

    @Bean
    public Binding permissionRevokeBinding() {
        return BindingBuilder
                .bind(permissionRevokeQueue())
                .to(internalTopicExchange())
                .with(permissionRevokeRoutingKey);
    }



    //    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//        // Implement a dummy EntityManagerFactory
//        return new DummyEntityManagerFactory();
//    }
}
