package com.lhu.rabbitmq.controller;

import com.lhu.rabbitmq.dto.MessageDataDto;
import com.lhu.rabbitmq.producer.MessageProducer;
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

  @GetMapping("publish-message/{message-type}/{message}")
  public ResponseEntity<String> publishMessage(
      @PathVariable("message-type") String messageType, @PathVariable("message") String message) {
    messageType = messageType.trim();
    message = message.trim();
    String responseString;

    if (messageType.equals("json")) {
      responseString =
          messageProducer.publishJsonMessage(
              (new MessageDataDto(UUID.randomUUID().toString(), message)));
    } else {
      responseString = messageProducer.publishStringMessage(message);
    }

    return ResponseEntity.ok(responseString);
  }
}
