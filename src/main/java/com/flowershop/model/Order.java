package com.flowershop.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Model class that represents a single order
 */
public class Order {

    private int orderId;
    private int userId;
    private LocalDate orderDate;
    private LocalDate shipDate;
    private BigDecimal totalPrice;

    public Order(int userId, LocalDate orderDate, LocalDate shipDate, BigDecimal totalPrice) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
        this.totalPrice = totalPrice;
    }

    public Order(int orderId, int userId, LocalDate orderDate, LocalDate shipDate, BigDecimal totalPrice) {
        this(userId, orderDate, shipDate, totalPrice);
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDate getShipDate() {
        return shipDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", orderDate=" + orderDate +
                ", shipDate=" + shipDate +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
