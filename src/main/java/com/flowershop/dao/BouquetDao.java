package com.flowershop.dao;

import com.flowershop.model.Bouquet;
import com.flowershop.model.Flower;
import com.flowershop.model.viewmodel.BouquetDetails;

import java.util.List;

/**
 * Interface for accessing Bouquet information from the persistence layer
 */
public interface BouquetDao {

    /**
     * Get all the bouquets available
     * @return list of all bouquets, empty list if none
     */
    List<Bouquet> getBouquets();

    /**
     * Get details on a single bouquet
     * @param bouquetId
     * @return Data on a single bouquet
     */
    BouquetDetails getBouquetById(int bouquetId);

    /**
     * Adds a new bouquet
     * @param bouquet
     * @return Data on a single bouquet
     */
    BouquetDetails addBouquet(BouquetDetails bouquet);

    /**
     * Get details on a single flower
     * @param flowerId
     * @return
     */
    Flower getFlowerById(int flowerId);
}
