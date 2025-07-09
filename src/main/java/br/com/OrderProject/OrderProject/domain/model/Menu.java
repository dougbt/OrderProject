package br.com.OrderProject.OrderProject.domain.model;

import java.util.List;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class Menu {
    private List<OrderItem> availableItems;
    
}
