package br.com.OrderProject.OrderProject.infrastructure.kafka;

import br.com.OrderProject.OrderProject.domain.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    @Value("${kafka.topic.orders}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    public void sendOrder(Order order) {
        kafkaTemplate.send(topic, order);
    }
}