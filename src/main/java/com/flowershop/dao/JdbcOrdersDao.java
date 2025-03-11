package com.flowershop.dao;

import com.flowershop.exception.DaoException;
import com.flowershop.model.Order;
import com.flowershop.model.viewmodel.OrderBouquet;
import com.flowershop.model.viewmodel.OrderDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class implementation of the {@link OrdersDao} interface that uses
 * JDBC for database interaction
 *
 * @see OrdersDao
 */
@Component
public class JdbcOrdersDao implements OrdersDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcOrdersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public OrderDetails getOrderById(int orderId){

        OrderDetails orderDetails = null;
        Order order = null;
        List<OrderBouquet> orderBouquets = new ArrayList<>();
        String sql =
                "SELECT o.order_id, o.user_id, o.order_date, o.ship_date, o.total_price, b.bouquet_id, b.name, b.base_price, bo.message " +
                "FROM orders AS o " +
                "JOIN bouquet_orders AS bo ON bo.order_id = o.order_id " +
                "JOIN bouquets AS b ON b.bouquet_id = bo.bouquet_id " +
                "WHERE o.order_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, orderId);

            while (results.next()) {

                if (order == null) {
                    order = mapRowToOrder(results);
                }
                orderBouquets.add(new OrderBouquet(
                        results.getInt("bouquet_id"),
                        results.getString("name"),
                        results.getBigDecimal("base_price"),
                        results.getString("message")
                ));
            }

            orderDetails = new OrderDetails(order, orderBouquets);
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch(Exception e) {
            throw new DaoException("Database error", e);
        }

        return orderDetails;
    }

    @Override
    public OrderDetails placeOrder(OrderDetails orderDetails) {

        OrderDetails orderConfirmation = null;
        Integer orderId = null;
        String orderInsertSql = "INSERT INTO orders (user_id, order_date, ship_date, total_price) VALUES " +
                "(?, ?, ?, ?) RETURNING order_id;";
        String bouquetOrdersInsertSql = "INSERT INTO bouquet_orders (order_id, bouquet_id, message) VALUES " +
                "(?, ?, ?);";

        try {
            /*
             * TODO: Insert into the Orders table first to get the order_id
             */
            orderId = jdbcTemplate.queryForObject(orderInsertSql, Integer.class,
                    orderDetails.getUserId(), orderDetails.getOrderDate(),
                    orderDetails.getShipDate(), orderDetails.getTotalPrice());

            /*
             * TODO: Insert into the many-to-many junction table once we have the order_id
             */
            for (OrderBouquet eachOrderBouquet : orderDetails.getOrderBouquets()) {
                jdbcTemplate.update(bouquetOrdersInsertSql, orderId,
                        eachOrderBouquet.getBouquetId(), eachOrderBouquet.getMessage());
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        catch(Exception e) {
            throw new DaoException("Database error", e);
        }

        /*
         * TODO: Read back the order to return
         */
        orderConfirmation = getOrderById(orderId);

        return orderConfirmation;
    }

    private Order mapRowToOrder(SqlRowSet results) {

        LocalDate orderDate = null;
        LocalDate shipDate = null;

        if(results.getDate("order_date") != null) {
            orderDate = results.getDate("order_date").toLocalDate();
        }
        if(results.getDate("ship_date") != null){
            shipDate = results.getDate("ship_date").toLocalDate();
        }

        return new Order(
                results.getInt("order_id"),
                results.getInt("user_id"),
                orderDate,
                shipDate,
                results.getBigDecimal("total_price")
        );
    }
}
