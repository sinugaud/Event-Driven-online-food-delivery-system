// File: OrderEvent.java
package com.ms.fooddelivery.delivery_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private Long orderId;
    private String eventType; // e.g., ORDER_CREATED, ORDER_UPDATED, etc.
    private Date timestamp;

}
