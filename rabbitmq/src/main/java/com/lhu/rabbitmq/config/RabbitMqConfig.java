package com.lhu.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
  public static final String JSON_QUEUE = "lhu_json_message_queue";
  public static final String STRING_QUEUE = "lhu_string_message_queue";
  public static final String EXCHANGE = "lhu_json_message_exchange";
  public static final String ROUTING_KEY_FOR_JSON = "json";
  public static final String ROUTING_KEY_FOR_STRING = "string";

  @Bean
  public Queue jsonQueue() {
    return new Queue(JSON_QUEUE);
  }

  @Bean
  public Queue stringQueue() {
    return new Queue(STRING_QUEUE);
  }

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(EXCHANGE);
  }

  @Bean
  public Binding bindingForJsonQueue(Queue jsonQueue, TopicExchange exchange) {
    return BindingBuilder.bind(jsonQueue).to(exchange).with(ROUTING_KEY_FOR_JSON);
  }

  @Bean
  public Binding bindingForStringQueue(Queue stringQueue, TopicExchange exchange) {
    return BindingBuilder.bind(stringQueue).to(exchange).with(ROUTING_KEY_FOR_STRING);
  }

  /*These Beans that required to work rabbitMQ broker.But These are automatically confined by Spring Framework.
  ConnectionFactory
  RabbitTemplate
  RabbitAdmin

  Only for specific configurations, It can be done as follows.
  */
  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public AmqpTemplate template(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
  }
}
