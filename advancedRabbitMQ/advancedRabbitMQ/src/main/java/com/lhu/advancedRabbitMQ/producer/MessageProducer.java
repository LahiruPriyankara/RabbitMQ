package com.lhu.advancedRabbitMQ.producer;

import com.lhu.advancedRabbitMQ.config.RabbitMqConfig;
import com.lhu.advancedRabbitMQ.dto.MessageDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProducer {
  private final RabbitTemplate rabbitTemplate;

  public String publishMessage(
      final String exchange, final String routingKey, final MessageDataDto messageDataDto) {
    try {
      rabbitTemplate.convertAndSend(exchange, routingKey, messageDataDto);
      return "Message successfully published json message..";
    } catch (Exception e) {
      return "Message published json message failed.." + e.getMessage();
    }
  }

  public String publishMessageByHeader(
      final String exchange, final String department, final MessageDataDto messageDataDto) {
    try {
      MessageProperties messageProperties = new MessageProperties();
      messageProperties.setHeader(RabbitMqConfig.DEPARTMENT, department);
      MessageConverter messageConverter = new Jackson2JsonMessageConverter();
      Message message = messageConverter.toMessage(messageDataDto, messageProperties);
      rabbitTemplate.send(exchange, "", message);

      return "Message successfully published json message by header..";

    } catch (Exception e) {
      return "Message published json message failed.." + e.getMessage();
    }
  }
}
