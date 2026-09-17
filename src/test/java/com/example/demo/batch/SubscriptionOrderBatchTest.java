package com.example.demo.batch;

import com.example.demo.entity.Order;
import com.example.demo.entity.Subscription;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SubscriptionOrderBatchTest {

    @Autowired
    private SubscriptionOrderBatch subscriptionOrderBatch;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void 定期購入データから注文が作成されること() {

        // 準備
        subscriptionRepository.deleteAll();
        orderRepository.deleteAll();

        Subscription subscription = new Subscription(
                1L,
                100L,
                2,
                LocalDate.of(2026, 7, 6),
                "ACTIVE"
        );

        subscriptionRepository.save(subscription);

        // 実行
        subscriptionOrderBatch.execute(LocalDate.of(2026, 7, 6));

        // 確認
        List<Order> orders = orderRepository.findAll();
        List<Subscription> subscriptions = subscriptionRepository.findAll();

        assertEquals(1, orders.size());
        assertEquals(1L, orders.get(0).getUserId());
        assertEquals(100L, orders.get(0).getProductId());
        assertEquals(2, orders.get(0).getQuantity());
        assertEquals(LocalDate.of(2026, 7, 6), orders.get(0).getOrderDate());
        assertEquals("SUBSCRIPTION", orders.get(0).getOrderType());

        assertEquals(LocalDate.of(2026, 8, 6), subscriptions.get(0).getNextOrderDate());
    }
}