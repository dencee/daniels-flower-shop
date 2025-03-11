package com.flowershop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    @JsonProperty("bouquet_orders")
    private List<BouquetOrderDto> bouquetOrders;

    public OrderDto(BigDecimal totalPrice, List<BouquetOrderDto> bouquetOrders) {
        this.totalPrice = totalPrice;
        this.bouquetOrders = bouquetOrders;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public List<BouquetOrderDto> getBouquetOrders() {
        return bouquetOrders;
    }
}
