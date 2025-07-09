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

    @KafkaListener(topics = "orders-topic", groupId = "order-group")
    public void processOrder(Order order) {
        try {
            orderRepository.save(order);
            System.out.println("Pedido salvo no banco: " + order.getId());
        } catch (Exception e) {
            // Fallback: armazena no Redis se o banco falhar
            redisTemplate.opsForList().rightPush("failed-orders", order);
            System.out.println("Pedido armazenado no Redis (fallback): " + order.getId());
        }
    }
}