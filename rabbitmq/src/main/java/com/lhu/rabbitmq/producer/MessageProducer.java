package com.lhu.rabbitmq.producer;

import com.lhu.rabbitmq.config.RabbitMqConfig;
import com.lhu.rabbitmq.dto.MessageDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProducer {
  private final RabbitTemplate rabbitTemplate;

  public String publishStringMessage(final String message) {
    try {
      rabbitTemplate.convertAndSend(
          RabbitMqConfig.EXCHANGE, RabbitMqConfig.ROUTING_KEY_FOR_STRING, message);

      return "Message successfully published string message..";
    } catch (Exception e) {
      return "Message published string message failed.." + e.getMessage();
    }
  }

  public String publishJsonMessage(final MessageDataDto messageDataDto) {
    try {
      rabbitTemplate.convertAndSend(
          RabbitMqConfig.EXCHANGE, RabbitMqConfig.ROUTING_KEY_FOR_JSON, messageDataDto);

      return "Message successfully published json message..";
    } catch (Exception e) {
      return "Message published json message failed.." + e.getMessage();
    }
  }
}
