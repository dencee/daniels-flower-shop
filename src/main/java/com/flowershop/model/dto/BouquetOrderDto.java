package com.flowershop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BouquetOrderDto {

    @JsonProperty("bouquet_id")
    private int bouquetId;
    private String message;

    public BouquetOrderDto(int bouquetId, String message) {
        this.bouquetId = bouquetId;
        this.message = message;
    }

    public int getBouquetId() {
        return bouquetId;
    }

    public String getMessage() {
        return message;
    }
}
