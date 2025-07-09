package br.com.OrderProject.OrderProject.domain.service;

import br.com.OrderProject.OrderProject.domain.model.Order;
import br.com.OrderProject.OrderProject.domain.repository.OrderRepository;
import br.com.OrderProject.OrderProject.infrastructure.kafka.KafkaProducerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Transactional
    public Order processOrder(Order order) {
        // Salva itens primeiro
        order.getItems().forEach(item -> item.setOrder(order));

        Order savedOrder = orderRepository.save(order);
        kafkaProducerService.sendOrder(savedOrder);
        return savedOrder;
    }
}