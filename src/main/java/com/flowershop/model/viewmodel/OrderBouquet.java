package com.flowershop.model.viewmodel;

import java.math.BigDecimal;

/**
 * Model class containing the ordered bouquet and the personalized message
 */
public class OrderBouquet {

    private int bouquetId;
    private String name;
    private BigDecimal basePrice;
    private String message;

    public OrderBouquet(int bouquetId, String name, BigDecimal basePrice, String message) {
        this.bouquetId = bouquetId;
        this.name = name;
        this.basePrice = basePrice;
        this.message = message;
    }

    public int getBouquetId() {
        return bouquetId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "OrderBouquet{" +
                "bouquetId=" + bouquetId +
                ", name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", message='" + message + '\'' +
                '}';
    }
}
