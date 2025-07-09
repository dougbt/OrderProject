package br.com.OrderProject.OrderProject.domain.service;

import br.com.OrderProject.OrderProject.domain.model.Order;
import br.com.OrderProject.OrderProject.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public Order processOrder(Order order) {
        kafkaProducerService.sendOrder(order);
        return orderRepository.save(order);
    }
}