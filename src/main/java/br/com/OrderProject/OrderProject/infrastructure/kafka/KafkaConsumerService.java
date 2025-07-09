package br.com.OrderProject.OrderProject.infrastructure.kafka;

import br.com.OrderProject.OrderProject.domain.model.Order;
import br.com.OrderProject.OrderProject.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RedisTemplate<String, Order> redisTemplate;

    @KafkaListener(topics = "${kafka.topic.orders}", groupId = "order-group")
    public void consumeOrder(Order order) {
        try {
            orderRepository.save(order);
        } catch (Exception e) {
            redisTemplate.opsForList().rightPush("orders-fallback", order);
        }
    }
}