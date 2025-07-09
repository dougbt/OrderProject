package br.com.OrderProject.OrderProject.domain.service;

import br.com.OrderProject.OrderProject.domain.model.Order;
import br.com.OrderProject.OrderProject.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order processOrder(Order order) {
        // Lógica de processamento do pedido (exemplo: salvar no banco)
        return orderRepository.save(order);
    }
} 