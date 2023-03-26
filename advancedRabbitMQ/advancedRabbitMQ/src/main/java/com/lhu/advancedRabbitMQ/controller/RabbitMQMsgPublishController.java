package com.lhu.advancedRabbitMQ.controller;

import com.lhu.advancedRabbitMQ.config.RabbitMqConfig;
import com.lhu.advancedRabbitMQ.dto.MessageDataDto;
import com.lhu.advancedRabbitMQ.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RabbitMQMsgPublishController {
  private final MessageProducer messageProducer;

  @GetMapping("publish-message/{exchange-name}/{routing-key}/{message}")
  public ResponseEntity<String> publishMessage(
      @PathVariable("exchange-name") String exchange,
      @PathVariable("routing-key") String routingKey,
      @PathVariable("message") String message) {
    exchange = exchange.trim();
    routingKey = routingKey.trim();
    message = message.trim();

    String response;
    if (!RabbitMqConfig.NORMAL_EXCHANGE.equals(exchange)) {
      response = "Exchange should be " + RabbitMqConfig.NORMAL_EXCHANGE;

    } else if (!(routingKey.isBlank()
        || RabbitMqConfig.MARKETING_QUEUE_ROUT_KEY.equals(routingKey)
        || RabbitMqConfig.FINANCE_QUEUE_ROUT_KEY.equals(routingKey)
        || RabbitMqConfig.ADMIN_QUEUE_ROUT_KEY.equals(routingKey))) {
      response =
          "Routing key should be one of (queue.marketing-key,queue.finance-key,queue.admin-key).Received:"
              + routingKey;

    } else {
      response =
          messageProducer.publishMessage(
              exchange, routingKey, (new MessageDataDto(UUID.randomUUID().toString(), message)));
    }

    return ResponseEntity.ok(response);
  }

  @GetMapping("publish-message/by-header/{exchange-name}/{department}/{message}")
  public ResponseEntity<String> publishMessageByHeader(
      @PathVariable("exchange-name") String exchange,
      @PathVariable("department") String department,
      @PathVariable("message") String message) {
    exchange = exchange.trim();
    department = department.trim();
    message = message.trim();

    String response;
    if (!RabbitMqConfig.HEADER_EXCHANGE.equals(exchange)) {
      response = "Exchange should be " + RabbitMqConfig.HEADER_EXCHANGE + ".Received:" + exchange;

    } else if (!(RabbitMqConfig.ADMIN.equals(department)
        || RabbitMqConfig.FINANCE.equals(department))) {
      response = "Department should be  one of (admin or finance).Received:" + department;

    } else {
      response =
          messageProducer.publishMessageByHeader(
              exchange, department, (new MessageDataDto(UUID.randomUUID().toString(), message)));
    }

    return ResponseEntity.ok(response);
  }
}
