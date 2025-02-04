package com.ms.fooddelivery.delivery_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryEvent implements Serializable {
    private Long orderId;
    private String deliveryStatus; // e.g., ASSIGNED, PICKED_UP, DELIVERED
    private Date timestamp;

}

