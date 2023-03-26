package com.lhu.advancedRabbitMQ.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
  // Exchanges
  public static final String NORMAL_EXCHANGE = "normal-exchange";
  public static final String HEADER_EXCHANGE = "header-exchange";
  // Queues
  public static final String MARKETING_QUEUE = "marketing-queue";
  public static final String FINANCE_QUEUE = "finance-queue";
  public static final String ADMIN_QUEUE = "admin-queue";
  public static final String ALL_QUEUE = "all-queue";
  // Queue binding keys
  public static final String MARKETING_QUEUE_ROUT_KEY = "queue.marketing-key";
  public static final String FINANCE_QUEUE_ROUT_KEY = "queue.finance-key";
  public static final String ADMIN_QUEUE_ROUT_KEY = "queue.admin-key";
  public static final String ALL_QUEUE_ROUT_KEY = "queue.*";

  public static final String DEPARTMENT = "department";
  public static final String FINANCE = "finance";
  public static final String ADMIN = "admin";

  // Exchanger - 1
  @Bean
  TopicExchange normalTopicExchange() {
    return new TopicExchange(NORMAL_EXCHANGE);
  }
  // --------------------------------------------------------------------------------------------

  // Queues set - marketingQueue,financeQueue,adminQueue,allQueue - START
  @Bean
  Queue marketingQueue() {
    return new Queue(MARKETING_QUEUE, false);
  }

  @Bean
  Queue financeQueue() {
    return new Queue(FINANCE_QUEUE, false);
  }

  @Bean
  Queue adminQueue() {
    return new Queue(ADMIN_QUEUE, false);
  }

  @Bean
  Queue allQueue() {
    return new Queue(ALL_QUEUE, false);
  }
  // Queues set - marketingQueue,financeQueue,adminQueue,allQueue - END
  // --------------------------------------------------------------------------------------------

  // Binding queues to 'Exchanger - 1' -- START
  @Bean
  Binding marketingBinding(Queue marketingQueue, TopicExchange normalTopicExchange) {
    return BindingBuilder.bind(marketingQueue)
        .to(normalTopicExchange)
        .with(MARKETING_QUEUE_ROUT_KEY);
  }

  @Bean
  Binding financeBinding(Queue financeQueue, TopicExchange normalTopicExchange) {
    return BindingBuilder.bind(financeQueue).to(normalTopicExchange).with(FINANCE_QUEUE_ROUT_KEY);
  }

  @Bean
  Binding adminBinding(Queue adminQueue, TopicExchange normalTopicExchange) {
    return BindingBuilder.bind(adminQueue).to(normalTopicExchange).with(ADMIN_QUEUE_ROUT_KEY);
  }

  @Bean
  Binding allBinding(Queue allQueue, TopicExchange normalTopicExchange) {
    return BindingBuilder.bind(allQueue).to(normalTopicExchange).with(ALL_QUEUE_ROUT_KEY);
  }
  // Binding queues to 'Exchanger - 1' -- END
  // --------------------------------------------------------------------------------------------

  // Header Exchange testing...[Exchanger - 2]
  @Bean
  HeadersExchange headerTopicExchange() {
    return new HeadersExchange("HEADER_EXCHANGE");
  }
  // --------------------------------------------------------------------------------------------

  // Binding Queues with Header Topic Exchange...Binding queues to 'Exchanger - 2' - START
  @Bean
  Binding marketingBindingForHeaderExchange(
      Queue marketingQueue, HeadersExchange headerTopicExchange) {
    return BindingBuilder.bind(marketingQueue)
        .to(headerTopicExchange)
        .where(DEPARTMENT)
        .matches(ADMIN);
  }

  @Bean
  Binding financeBindingForHeaderExchange(Queue financeQueue, HeadersExchange headerTopicExchange) {
    return BindingBuilder.bind(financeQueue)
        .to(headerTopicExchange)
        .where(DEPARTMENT)
        .matches(FINANCE);
  }
  // Binding Queues with Header Topic Exchange...Binding queues to 'Exchanger - 2' - END
  // --------------------------------------------------------------------------------------------

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
