package br.com.OrderProject.OrderProject.domain.service;

import br.com.OrderProject.OrderProject.domain.model.Menu;
import br.com.OrderProject.OrderProject.domain.repository.OrderItemRepository;
import br.com.OrderProject.OrderProject.domain.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    public Menu getMenu() {
        List<OrderItem> items = orderItemRepository.findAll();
        return new Menu(items);
    }
} 