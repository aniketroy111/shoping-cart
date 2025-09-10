package com.shopping.cart.dto;

import com.shopping.cart.model.Category;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private String brand;
    private String description;
    private Long price;
    private Integer inventory;
    private Category category;
    private List<ImageDto> images;
}
