package br.com.OrderProject.OrderProject.domain.service;

import org.springframework.stereotype.Service;

@Service
public class FallbackService {
    public void reprocessFailedOrders() {
        // Lógica para reprocessar pedidos com falha do Redis
    }
} 