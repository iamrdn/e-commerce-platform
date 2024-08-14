package com.altimetrik.order.service;

import com.altimetrik.order.model.Order;
import com.altimetrik.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    private static final String TOPIC = "order_topic";

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        kafkaTemplate.send(TOPIC, savedOrder);
        return savedOrder;
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
