package com.example.demo.batch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BatchResult {

    private final LocalDate targetDate;
    private final int processedCount;
    private final List<OrderSummary> orders;
    private final LocalDateTime executedAt;

    public BatchResult(LocalDate targetDate, List<OrderSummary> orders) {
        this.targetDate = targetDate;
        this.orders = orders;
        this.processedCount = orders.size();
        this.executedAt = LocalDateTime.now();
    }

    public LocalDate getTargetDate() { return targetDate; }
    public int getProcessedCount() { return processedCount; }
    public List<OrderSummary> getOrders() { return orders; }
    public LocalDateTime getExecutedAt() { return executedAt; }

    public static class OrderSummary {
        private final Long orderId;
        private final Long userId;
        private final Long productId;
        private final int quantity;

        public OrderSummary(Long orderId, Long userId, Long productId, int quantity) {
            this.orderId = orderId;
            this.userId = userId;
            this.productId = productId;
            this.quantity = quantity;
        }

        public Long getOrderId() { return orderId; }
        public Long getUserId() { return userId; }
        public Long getProductId() { return productId; }
        public int getQuantity() { return quantity; }
    }
}
