package br.com.OrderProject.OrderProject.domain.service;

import br.com.OrderProject.OrderProject.domain.model.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "orders", groupId = "order_group")
    public void consumeOrder(Order order) {
        // Lógica para consumir e processar o pedido
    }
} 