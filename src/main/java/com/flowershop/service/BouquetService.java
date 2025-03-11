package com.flowershop.service;

import com.flowershop.dao.BouquetDao;
import com.flowershop.exception.DaoException;
import com.flowershop.model.Bouquet;
import com.flowershop.model.Flower;
import com.flowershop.model.dto.BouquetDto;
import com.flowershop.model.dto.FlowerDto;
import com.flowershop.model.viewmodel.BouquetDetails;
import com.flowershop.model.viewmodel.BouquetFlower;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for setting up Bouquet CRUD operations
 */
@Service
public class BouquetService {

    private BouquetDao bouquetDao;

    public BouquetService(BouquetDao bouquetDao) {
        this.bouquetDao = bouquetDao;
    }

    /*
     * TODO: @Transactional sets up a SQL TRANSACTION and
     *  rolls back if any DaoException occurs
     */
    @Transactional(rollbackFor = DaoException.class)
    public BouquetDetails addBouquet(BouquetDto bouquetDto){

        List<BouquetFlower> bouquetFlowers = new ArrayList<>();
        List<FlowerDto> flowers = bouquetDto.getFlowers();

        /*
         * TODO: Go through each flower in the JSON request body
         *  there are 3 objects in the Postman sample request
         */
        for(FlowerDto eachFlower : flowers){

            Flower flower = bouquetDao.getFlowerById(eachFlower.getFlowerId());
            bouquetFlowers.add(new BouquetFlower(
                    flower.getFlowerId(),
                    flower.getName(),
                    flower.getColor(),
                    flower.getPrice(),
                    eachFlower.getQuantity()
            ));
        }

        /*
         * TODO: Construct the complete, detailed bouquet before adding
         */
        Bouquet newBouquet = new Bouquet(bouquetDto.getName(), bouquetDto.getMessage(), bouquetDto.getBasePrice(), bouquetDto.getImageUrl());
        BouquetDetails bouquetToAdd = new BouquetDetails(newBouquet, bouquetFlowers);

        /*
         * TODO: Add the complete Bouquet
         */
        BouquetDetails addedBouquet = bouquetDao.addBouquet(bouquetToAdd);

        return addedBouquet;
    }
}
