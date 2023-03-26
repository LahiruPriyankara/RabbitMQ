package com.lhu.advancedRabbitMQ.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDataDto {
  private String id;
  private String message;
}
