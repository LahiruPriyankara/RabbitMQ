package com.lhu.advancedRabbitMQ.consumer;

import com.lhu.advancedRabbitMQ.config.RabbitMqConfig;
import com.lhu.advancedRabbitMQ.dto.MessageDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageConsumer {

  /*specificMarketingQueueConsumerOne and specificMarketingQueueConsumerTwo are for to check round robbin*/
  @RabbitListener(queues = RabbitMqConfig.MARKETING_QUEUE)
  public void specificMarketingQueueConsumerOne(MessageDataDto message) {
    try {
      //Thread.sleep(1000);
      log.info("MessageConsumer.specificMarketingQueueConsumerONE.Message:" + message);
    } catch (Exception e) {
      System.out.println("Exception :" + e);
    }
  }

  @RabbitListener(queues = RabbitMqConfig.MARKETING_QUEUE)
  public void specificMarketingQueueConsumerTwo(MessageDataDto message) {
    try {
      //Thread.sleep(5000);
      log.info("MessageConsumer.specificMarketingQueueConsumerTWO.Message:" + message);
    } catch (Exception e) {
      System.out.println("Exception :" + e);
    }
  }

  @RabbitListener(queues = RabbitMqConfig.FINANCE_QUEUE)
  public void financeQueueConsumer(MessageDataDto message) {
    log.info("MessageConsumer.financeQueueConsumer.Message:" + message);
  }

  @RabbitListener(queues = RabbitMqConfig.ADMIN_QUEUE)
  public void adminQueueConsumer(MessageDataDto message) {
    log.info("MessageConsumer.adminQueueConsumer.Message:" + message);
  }

  @RabbitListener(queues = RabbitMqConfig.ALL_QUEUE)
  public void allQueueConsume(MessageDataDto message) {
    log.info("MessageConsumer.allQueueConsume.Message:" + message);
  }
}
