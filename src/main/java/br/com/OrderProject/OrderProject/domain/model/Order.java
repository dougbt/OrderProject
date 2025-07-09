package br.com.OrderProject.OrderProject.domain.model;

import java.time.LocalDateTime;
import java.util.List;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class Order {
    private Long id;
    private String customerId;
    private List<OrderItem> items; 
    private LocalDateTime createdAt;
    private String status; //TODO: Criar enum para status

}
