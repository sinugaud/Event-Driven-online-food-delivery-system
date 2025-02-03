// File: DeliveryEvent.java
package com.ms.fooddelivery.notification_service.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class DeliveryEvent implements Serializable {
    private Long orderId;
    private String deliveryStatus; // e.g., ASSIGNED, PICKED_UP, DELIVERED
    private Date timestamp;

    public DeliveryEvent(Long orderId, String assigned, Date date) {
    }
}

