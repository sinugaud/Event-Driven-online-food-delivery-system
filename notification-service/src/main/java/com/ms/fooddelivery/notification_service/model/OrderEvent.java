package com.ms.fooddelivery.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent  implements Serializable {
  private Long orderId;
  private String eventType; // e.g., ORDER_CREATED, ORDER_CONFIRMED, ORDER_DISPATCHED, ORDER_DELIVERED
  private Date timestamp;



}
