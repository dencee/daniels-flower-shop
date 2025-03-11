package com.flowershop.model.viewmodel;

import com.flowershop.model.Flower;

import java.math.BigDecimal;

/**
 * Model class for a view that includes a Flower and it's quantity in a Bouquet
 */
public class BouquetFlower extends Flower {

    private int quantity;

    public BouquetFlower(int flowerId, String name, String color, BigDecimal price, int quantity) {
        super(flowerId, name, color, price);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
