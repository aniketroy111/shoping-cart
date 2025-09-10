package com.shopping.cart.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private BigDecimal unitPrice;
    private Integer quantity;
}
