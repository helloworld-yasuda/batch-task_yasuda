package com.example.demo.batch;

import com.example.demo.entity.Order;
import com.example.demo.entity.Subscription;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class SubscriptionOrderBatch {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionOrderBatch.class);

    private final SubscriptionRepository subscriptionRepository;
    private final OrderRepository orderRepository;

    public SubscriptionOrderBatch(
            SubscriptionRepository subscriptionRepository,
            OrderRepository orderRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public BatchResult execute(LocalDate targetDate) {

        log.info("========== 定期購入注文作成バッチ 開始 ==========");
        log.info("対象日: {}", targetDate);

        List<Subscription> subscriptions =
                subscriptionRepository.findByNextOrderDateAndStatus(targetDate, "ACTIVE");

        log.info("対象サブスクリプション件数: {}", subscriptions.size());

        List<BatchResult.OrderSummary> summaries = new ArrayList<>();

        for (Subscription subscription : subscriptions) {

            Order order = new Order(
                    subscription.getUserId(),
                    subscription.getProductId(),
                    subscription.getQuantity(),
                    targetDate,
                    "SUBSCRIPTION"
            );

            Order saved = orderRepository.save(order);
            subscription.setNextOrderDate(targetDate.plusMonths(1));

            summaries.add(new BatchResult.OrderSummary(
                    saved.getId(),
                    saved.getUserId(),
                    saved.getProductId(),
                    saved.getQuantity()
            ));

            log.info("  [注文作成] orderId={} userId={} productId={} quantity={} nextOrderDate={}",
                    saved.getId(), saved.getUserId(), saved.getProductId(),
                    saved.getQuantity(), subscription.getNextOrderDate());
        }

        log.info("作成注文数: {}", summaries.size());
        log.info("========== 定期購入注文作成バッチ 終了 ==========");

        return new BatchResult(targetDate, summaries);
    }
}
