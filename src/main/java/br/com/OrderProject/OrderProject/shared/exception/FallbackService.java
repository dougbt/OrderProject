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
    private OrderRepository orderRepository;
    @Autowired
    private RedisTemplate<String, Order> redisTemplate;

    public void reprocessFailedOrders() {
        List<Order> orders = redisTemplate.opsForList().range("orders-fallback", 0, -1);
        orders.forEach(orderRepository::save);
        redisTemplate.delete("orders-fallback");
    }
}