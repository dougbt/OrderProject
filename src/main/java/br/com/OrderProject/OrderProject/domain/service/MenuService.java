package br.com.OrderProject.OrderProject.domain.service;

import br.com.OrderProject.OrderProject.domain.model.Menu;
import br.com.OrderProject.OrderProject.domain.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MenuService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private RedisTemplate<String, Menu> redisTemplate;

    private static final String CACHE_KEY = "menu";

    public Menu getMenu() {
        Menu menu = redisTemplate.opsForValue().get(CACHE_KEY);
        if (menu == null) {
            menu = new Menu(orderItemRepository.findAll());
            redisTemplate.opsForValue().set(CACHE_KEY, menu, 1, TimeUnit.HOURS);
        }
        return menu;
    }

    public void clearMenuCache() {
        // Limpa o cache quando houver alterações no db
        redisTemplate.delete(CACHE_KEY);
    }
}