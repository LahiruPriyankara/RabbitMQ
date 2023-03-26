package com.lhu.rabbitmq.consumer;

import com.lhu.rabbitmq.config.RabbitMqConfig;
import com.lhu.rabbitmq.dto.MessageDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageConsumer {

  @RabbitListener(queues = RabbitMqConfig.STRING_QUEUE)
  public void stringMessageListener(String message) {
    log.info("String message consume from RabbitMQ[STRING_QUEUE].Message:" + message);
  }

  @RabbitListener(queues = RabbitMqConfig.JSON_QUEUE)
  public void jsonMessageListener(MessageDataDto message) {
    String strMessage = message != null ? message.toString() : null;
    log.info("Json message consume from RabbitMQ[JSON_QUEUE].Message:" + strMessage);
  }
}
