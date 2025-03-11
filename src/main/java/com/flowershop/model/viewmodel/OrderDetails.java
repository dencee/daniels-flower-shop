package com.flowershop.model.viewmodel;

import com.flowershop.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Model class containing the Order and the corresponding Bouquets in the order
 */
public class OrderDetails extends Order {

    private List<OrderBouquet> orderBouquets;

    public OrderDetails(int userId, LocalDate orderDate, LocalDate shipDate, BigDecimal totalPrice, List<OrderBouquet> orderBouquets) {
        super(userId, orderDate, shipDate, totalPrice);
        this.orderBouquets = orderBouquets;
    }

    public OrderDetails(Order order, List<OrderBouquet> orderBouquets) {
        super(order.getOrderId(), order.getUserId(), order.getOrderDate(), order.getShipDate(), order.getTotalPrice());
        this.orderBouquets = orderBouquets;
    }

    public List<OrderBouquet> getOrderBouquets() {
        return orderBouquets;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderBouquets=" + orderBouquets +
                '}';
    }
}
