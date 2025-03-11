package com.flowershop.controller;

import com.flowershop.exception.DaoException;
import com.flowershop.exception.UnauthorizedUserAccess;
import com.flowershop.exception.UserNotFoundException;
import com.flowershop.model.dto.OrderDto;
import com.flowershop.model.viewmodel.OrderDetails;
import com.flowershop.service.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

/**
 * Controller used for all /orders endpoints
 */
@PreAuthorize("isAuthenticated()")
@RestController
public class OrdersController {

    private OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @RequestMapping(path = "/orders/{orderId}", method = RequestMethod.GET)
    public OrderDetails getOrderById(@PathVariable int orderId, Principal principal){

        OrderDetails confirmationOrder = null;
        try {
            confirmationOrder = this.ordersService.getOrderById(orderId, principal.getName());
        }
        catch(UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: user ( " + principal.getName() + ") not found");
        }
        catch(UnauthorizedUserAccess e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ERROR: access to other user orders denied");
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.PARTIAL_CONTENT, "ERROR: Unable to get order id: " + orderId);
        }

        return confirmationOrder;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/orders", method = RequestMethod.POST)
    public OrderDetails createOrder(@RequestBody OrderDto order, Principal principal){

        OrderDetails confirmationOrder = null;
        try {
            confirmationOrder = this.ordersService.placeOrder(order, principal.getName());
        }
        catch(UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR: user ( " + principal.getName() + ") not found");
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.PARTIAL_CONTENT, "ERROR: Unable to save order: " + e.getMessage());
        }

        return confirmationOrder;
    }
}
