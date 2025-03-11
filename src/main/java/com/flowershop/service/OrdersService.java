package com.flowershop.service;

import com.flowershop.dao.BouquetDao;
import com.flowershop.dao.OrdersDao;
import com.flowershop.dao.UserDao;
import com.flowershop.exception.DaoException;
import com.flowershop.exception.UnauthorizedUserAccess;
import com.flowershop.exception.UserNotFoundException;
import com.flowershop.model.User;
import com.flowershop.model.UserProfile;
import com.flowershop.model.dto.BouquetOrderDto;
import com.flowershop.model.dto.OrderDto;
import com.flowershop.model.viewmodel.BouquetDetails;
import com.flowershop.model.viewmodel.OrderBouquet;
import com.flowershop.model.viewmodel.OrderDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class to support CRUD operations for Orders
 */
@Service
public class OrdersService {

    private UserDao userDao;
    private BouquetDao bouquetDao;
    private OrdersDao ordersDao;
    private EmailService emailService;

    public OrdersService(UserDao userDao, BouquetDao bouquetDao, OrdersDao ordersDao, EmailService emailService) {
        this.userDao = userDao;
        this.bouquetDao = bouquetDao;
        this.ordersDao = ordersDao;
        this.emailService = emailService;
    }

    /**
     * Return details on a specific order.
     * @param orderId the id of the order
     * @param username username requesting the order
     * @return Order details
     */
    public OrderDetails getOrderById(int orderId, String username){

        User user = validateUsername(username);
        OrderDetails orderDetails = ordersDao.getOrderById(orderId);

        /*
         * TODO: Make sure the user is accessing their own order
         */
        if(orderDetails.getUserId() != user.getId()) {
            throw new UnauthorizedUserAccess("ERROR: Unauthorized order access");
        }

        return orderDetails;
    }

    /**
     * Transactional sets up a SQL TRANSACTION and
     * rolls back if any DaoException occurs.
     * @param order order data to add
     * @param username username of client making the request
     * @return Order details
     */
    @Transactional(rollbackFor = DaoException.class)
    public OrderDetails placeOrder(OrderDto order, String username) {

        User user = validateUsername(username);
        UserProfile userProfile = userDao.getUserProfileByUserId(user.getId());

        if(userProfile == null){
            throw new UserNotFoundException("ERROR: user not found " + username);
        }

        /*
         * TODO: Get additional information on order (Bouquet info)
         *  before adding the complete order
         */
        List<OrderBouquet> orderBouquets = new ArrayList<>();

        for(BouquetOrderDto eachOrder : order.getBouquetOrders()){

            BouquetDetails bouquet = bouquetDao.getBouquetById(eachOrder.getBouquetId());
            orderBouquets.add(new OrderBouquet(
                    bouquet.getBouquetId(),
                    bouquet.getName(),
                    bouquet.getBasePrice(),
                    eachOrder.getMessage())
            );
        }

        /*
         * TODO: Add complete order to database
         */
        OrderDetails orderDetails = new OrderDetails(user.getId(), LocalDate.now(), null, order.getTotalPrice(), orderBouquets);
        OrderDetails orderConfirmation = ordersDao.placeOrder(orderDetails);

        /*
         * TODO: Call External API to send email notification after placing order
         */
        if(orderConfirmation != null){
            emailService.sendOrderConfirmationEmail(userProfile.getEmail(), orderConfirmation);
        }

        return orderConfirmation;
    }

    /**
     * Helper method to verify valid username is entered.
     * @param username username to validate
     * @return User information
     */
    private User validateUsername(String username) throws UserNotFoundException {

        User user = userDao.getUserByUsername(username);

        if(user == null){
            throw new UserNotFoundException("ERROR: user not found " + username);
        }

        return user;
    }
}
