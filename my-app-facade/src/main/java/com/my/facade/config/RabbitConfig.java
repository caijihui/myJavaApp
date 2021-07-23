package com.my.facade.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);// 废弃
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    //配置队列Queue   即 routing-key
    @Bean
    public Queue Queue() {
        return new Queue("hello");
    }

    @Bean
    public Queue Queue1() {
        return new Queue("hello1");
    }

    @Bean
    public Queue queueMessage() {
        return new Queue("topic.message");
    }

    @Bean
    public Queue queueMessages() {
        return new Queue("topic.messages");
    }

    //配置交换机Exchange
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    TopicExchange exchange1() {
        return new TopicExchange("exchange1");
    }


    @Bean
    public Queue fanoutQueue1() {
        return new Queue("fanoutQueue1");
    }

    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout_to_exchange", true, false);
    }

    //   exchange  绑定  topic.message
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }


    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }


    // 队列绑定交换机
    @Bean
    public Binding bindFanout1(){
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

}
