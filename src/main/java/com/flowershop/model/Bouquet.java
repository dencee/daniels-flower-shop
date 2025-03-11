package com.flowershop.model;

import java.math.BigDecimal;

/**
 * Model class representing a bouquet of flowers
 */
public class Bouquet {

    private int bouquetId;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private String imageUrl;

    public Bouquet(String name, String description, BigDecimal basePrice, String imageUrl) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.imageUrl = imageUrl;
    }

    public Bouquet(int bouquetId, String name, String description, BigDecimal basePrice, String imageUrl) {
        this(name, description, basePrice, imageUrl);
        this.bouquetId = bouquetId;
    }

    public int getBouquetId() {
        return bouquetId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
