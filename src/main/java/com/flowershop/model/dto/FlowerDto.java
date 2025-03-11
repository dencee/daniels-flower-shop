package com.flowershop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowerDto {

    @JsonProperty("flower_id")
    private int flowerId;
    private int quantity;

    public FlowerDto(int flowerId, int quantity) {
        this.flowerId = flowerId;
        this.quantity = quantity;
    }

    public int getFlowerId() {
        return flowerId;
    }

    public int getQuantity() {
        return quantity;
    }
}
