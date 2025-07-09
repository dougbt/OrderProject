package br.com.OrderProject.OrderProject.domain.model;

import java.math.BigDecimal;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class OrderItem {
    private Long id;
    private String name;
    private BigDecimal price;
    private String category; // "MEAL", "DRINK", etc. //todo criar enum para categoria
}
