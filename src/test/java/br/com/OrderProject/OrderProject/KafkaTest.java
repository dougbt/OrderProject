package br.com.OrderProject.OrderProject;

import br.com.OrderProject.OrderProject.domain.model.Menu;
import br.com.OrderProject.OrderProject.domain.model.Order;
import br.com.OrderProject.OrderProject.domain.model.OrderItem;
import br.com.OrderProject.OrderProject.domain.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=localhost:9092",
        "spring.kafka.consumer.auto-offset-reset=earliest"
})
class KafkaTest {

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    @Autowired
    private MenuService menuService;

    @Test
    void testSendOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setCustomerId("1");
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setItems(new ArrayList<>());

        kafkaTemplate.send("orders", order);
    }

    @Test
    void testKafkaProducerService() {
        Order order = new Order();
        order.setId(2L);
        order.setCustomerId("2");
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING");

        List<OrderItem> items = new ArrayList<>();
        OrderItem item = new OrderItem();
        item.setId(1L);
        item.setName("Hamburger");
        item.setPrice(new BigDecimal("25.50"));
        item.setCategory("MEAL");
        items.add(item);
        order.setItems(items);

    }

    @Test
    void testMenuCache() {
        Menu firstCall = menuService.getMenu(); // Deve buscar do DB
        Menu secondCall = menuService.getMenu(); // Deve vir do cache
        assertThat(firstCall).isNotNull();
        assertThat(secondCall).isNotNull();
    }

    @Test
    void testOrderWithItems() {
        Order order = new Order();
        order.setId(3L);
        order.setCustomerId("3");
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING");

        List<OrderItem> items = new ArrayList<>();
        OrderItem item1 = new OrderItem();
        item1.setId(1L);
        item1.setName("Coca-Cola");
        item1.setPrice(new BigDecimal("8.50"));
        item1.setCategory("DRINK");
        items.add(item1);

        OrderItem item2 = new OrderItem();
        item2.setId(2L);
        item2.setName("Batata Frita");
        item2.setPrice(new BigDecimal("12.00"));
        item2.setCategory("SIDE");
        items.add(item2);

        order.setItems(items);

    }

    @Test
    void testOrderStatusTransitions() {
        Order order = new Order();
        order.setId(4L);
        order.setCustomerId("4");
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setItems(new ArrayList<>());

        // Teste de transição de status
        assertThat(order.getStatus()).isEqualTo("PENDING");

        order.setStatus("PROCESSING");
        assertThat(order.getStatus()).isEqualTo("PROCESSING");

        order.setStatus("COMPLETED");
        assertThat(order.getStatus()).isEqualTo("COMPLETED");
    }
}