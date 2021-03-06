package cn.nest.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * Created by botter
 *
 * @Date 11/11/16.
 * @description
 */
@Configuration
@EnableAutoConfiguration
public class RabbitMQConfig implements RabbitListenerConfigurer {

    public static final String MQ_APP_QUEUE = "appQueue";

    public static final String MQ_UPGRADE_QUEUE = "upgradeQueue";

    public static final String MQ_EXCHANGE = "DmExchange";

    public static final String MQ_EXCHANGE_APP_KEY = "DmAppExchangeKey";

    public static final String MQ_EXCHANGE_UPGRADE_KEY = "DmUpgrdeExchangeKey";

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public Queue appQueue() {
        return new Queue(MQ_APP_QUEUE, false);
    }

    @Bean
    public Queue upgradeQueue() {
        return new Queue(MQ_UPGRADE_QUEUE, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MQ_EXCHANGE);
    }

    @Bean
    public Binding bingUpgrade(TopicExchange topicExchange, Queue upgradeQueue) {
        return BindingBuilder.bind(upgradeQueue).to(topicExchange).with(MQ_EXCHANGE_UPGRADE_KEY);
    }


    @Bean
    public Binding bingApp(TopicExchange topicExchange, Queue appQueue) {
        return BindingBuilder.bind(appQueue).to(topicExchange).with(MQ_EXCHANGE_APP_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setExchange(MQ_EXCHANGE);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitMessagingTemplate createRabbitMessageTemplate(RabbitTemplate rabbitTemplate) {
        RabbitMessagingTemplate rabbitMessagingTemplate = new RabbitMessagingTemplate();
        rabbitMessagingTemplate.setRabbitTemplate(rabbitTemplate);
        return rabbitMessagingTemplate;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory defaultMessageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(new MappingJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(defaultMessageHandlerMethodFactory());
    }
}
