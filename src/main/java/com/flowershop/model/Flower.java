package com.flowershop.model;

import java.math.BigDecimal;

/**
 * Model class that represents a single flower in a Bouquet
 */
public class Flower {

    private int flowerId;
    private String name;
    private String color;
    private BigDecimal price;

    public Flower(int flowerId, String name, String color, BigDecimal price) {
        this.flowerId = flowerId;
        this.name = name;
        this.color = color;
        this.price = price;
    }

    public int getFlowerId() {
        return flowerId;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
