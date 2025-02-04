// File: DeliveryEvent.java
package com.ms.fooddelivery.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryEvent implements Serializable {
    private Long orderId;
    private String deliveryStatus; // e.g., ASSIGNED, PICKED_UP, DELIVERED
    private Date timestamp;
}

