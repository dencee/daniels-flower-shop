package com.flowershop.controller;

import com.flowershop.dao.BouquetDao;
import com.flowershop.exception.DaoException;
import com.flowershop.model.Bouquet;
import com.flowershop.model.dto.BouquetDto;
import com.flowershop.model.viewmodel.BouquetDetails;
import com.flowershop.service.BouquetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controller used for all /bouquets endpoints
 */
@PreAuthorize("isAuthenticated()")
@RestController
public class BouquetController {

    BouquetDao bouquetDao;
    BouquetService bouquetService;

    public BouquetController(BouquetDao bouquetDao, BouquetService bouquetService) {
        this.bouquetDao = bouquetDao;
        this.bouquetService = bouquetService;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/bouquets", method = RequestMethod.GET)
    public List<Bouquet> getBouquets(){

        List<Bouquet> bouquets = bouquetDao.getBouquets();
        return bouquets;
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/bouquets/{bouquetId}", method = RequestMethod.GET)
    public BouquetDetails getBouquetById(@PathVariable int bouquetId){

        BouquetDetails bouquetDetails = null;
        try {
            bouquetDetails = bouquetDao.getBouquetById(bouquetId);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: Unable to get bouquet id: " + bouquetId);
        }

        return bouquetDetails;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/bouquets", method = RequestMethod.POST)
    public BouquetDetails addBouquet(@RequestBody @Valid BouquetDto bouquet){

        BouquetDetails addedBouquet = null;
        addedBouquet = bouquetService.addBouquet(bouquet);

        return addedBouquet;
    }
}
