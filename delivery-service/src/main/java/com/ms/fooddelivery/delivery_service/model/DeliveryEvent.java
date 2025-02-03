// File: DeliveryEvent.java
package com.ms.fooddelivery.delivery_service.model;

import java.util.Date;

public class DeliveryEvent {
    private Long orderId;
    private String deliveryStatus; // e.g., ASSIGNED, PICKED_UP, DELIVERED
    private Date timestamp;

    public DeliveryEvent(Long orderId, String assigned, Date date) {
    }
}

