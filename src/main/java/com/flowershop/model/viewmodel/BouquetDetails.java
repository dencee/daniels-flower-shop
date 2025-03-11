package com.flowershop.model.viewmodel;

import com.flowershop.model.Bouquet;

import java.util.List;

/**
 * Model class containing the bouquet, each flower, and quantity of each flower
 */
public class BouquetDetails extends Bouquet {

    List<BouquetFlower> bouquetFlowers;

    public BouquetDetails(Bouquet bouquet, List<BouquetFlower> flowers) {
        super(bouquet.getBouquetId(), bouquet.getName(), bouquet.getDescription(),
                bouquet.getBasePrice(), bouquet.getImageUrl());
        this.bouquetFlowers = flowers;
    }

    public List<BouquetFlower> getBouquetFlowers() {
        return bouquetFlowers;
    }
}
