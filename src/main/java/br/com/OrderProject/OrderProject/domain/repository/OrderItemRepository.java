package br.com.OrderProject.OrderProject.domain.repository;

import br.com.OrderProject.OrderProject.domain.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
} 