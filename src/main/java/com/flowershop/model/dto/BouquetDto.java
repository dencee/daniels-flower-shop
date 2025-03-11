package com.flowershop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO class used when
 */
public class BouquetDto {

    @NotBlank
    private String name;
    private String message;

    // DTO valid checks
    // price > 0
    @JsonProperty("base_price")
    private BigDecimal basePrice;

    @JsonProperty("image_url")
    private String imageUrl;

    List<FlowerDto> flowers;

    public BouquetDto(String name, String message, BigDecimal basePrice, String imageUrl, List<FlowerDto> flowers) {
        this.name = name;
        this.message = message;
        this.basePrice = basePrice;
        this.imageUrl = imageUrl;
        this.flowers = flowers;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<FlowerDto> getFlowers() {
        return flowers;
    }
}
