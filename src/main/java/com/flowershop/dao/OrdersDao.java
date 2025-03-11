package com.flowershop.dao;

import com.flowershop.model.viewmodel.OrderDetails;

/**
 * Interface for accessing Order information from the persistence layer
 */
public interface OrdersDao {

    /**
     * Get specific order that has been placed
     * @param orderId
     * @return Order details or null if not found
     */
    OrderDetails getOrderById(int orderId);

    /**
     * Create an order
     * @param orderDetails
     * @return Order details after creating
     */
    OrderDetails placeOrder(OrderDetails orderDetails);
}
