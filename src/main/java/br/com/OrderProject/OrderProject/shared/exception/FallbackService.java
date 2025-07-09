package br.com.OrderProject.OrderProject.shared.exception;

import br.com.OrderProject.OrderProject.domain.model.Order;
import br.com.OrderProject.OrderProject.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FallbackService {
    @Autowired
    private RedisTemplate<String, Order> redisTemplate;

    @Autowired
    private OrderRepository orderRepository;

    public void reprocessFailedOrders() {
        List<Order> failedOrders = redisTemplate.opsForList().range("failed-orders", 0, -1);
        failedOrders.forEach(order -> {
            try {
                orderRepository.save(order);
            } catch (Exception e) {
                // TODO TRATAR EXCEÇÃO(REGISTRAR LOG OU NOTIFICAR ADMINISTRADOR)
            }
        });
        redisTemplate.delete("failed-orders");
    }
}