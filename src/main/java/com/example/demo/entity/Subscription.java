package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long productId;

    private Integer quantity;

    private LocalDate nextOrderDate;

    private String status;

    public Subscription() {
    }

    public Subscription(Long userId, Long productId, Integer quantity, LocalDate nextOrderDate, String status) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.nextOrderDate = nextOrderDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDate getNextOrderDate() {
        return nextOrderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setNextOrderDate(LocalDate nextOrderDate) {
        this.nextOrderDate = nextOrderDate;
    }
}