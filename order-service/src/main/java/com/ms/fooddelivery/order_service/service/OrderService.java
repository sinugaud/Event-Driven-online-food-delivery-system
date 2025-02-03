package com.ms.fooddelivery.order_service.service;

import com.ms.fooddelivery.order_service.model.Order;
import com.ms.fooddelivery.order_service.model.OrderEvent;
import com.ms.fooddelivery.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private KafkaProducer kafkaProducer;

  public Order createOrder(Order order) {
    order.setStatus("CREATED");
    Order savedOrder = orderRepository.save(order);
    OrderEvent event = new OrderEvent(savedOrder.getId(), "ORDER_CREATED", new Date());
    kafkaProducer.sendOrderEvent(event);
    return savedOrder;
  }

  public Order updateOrder(Long orderId, Order orderUpdate) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));
    // Update order fields (for simplicity, updating only details here)
    order.setDetails(orderUpdate.getDetails());
    order.setStatus("UPDATED");
    Order savedOrder = orderRepository.save(order);
    OrderEvent event = new OrderEvent(savedOrder.getId(), "ORDER_UPDATED", new Date());
    kafkaProducer.sendOrderEvent(event);
    return savedOrder;
  }

  public void cancelOrder(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));
    order.setStatus("CANCELLED");
    orderRepository.save(order);
    OrderEvent event = new OrderEvent(orderId, "ORDER_CANCELLED", new Date());
    kafkaProducer.sendOrderEvent(event);
  }
}
