package br.com.OrderProject.OrderProject.domain.repository;

import br.com.OrderProject.OrderProject.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
