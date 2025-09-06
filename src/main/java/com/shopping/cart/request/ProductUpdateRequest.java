package com.shopping.cart.request;

import com.shopping.cart.dto.ImageDTO;
import com.shopping.cart.model.Category;
import com.shopping.cart.model.Image;
import lombok.Data;

import java.util.List;

@Data
public class ProductUpdateRequest {

    private Long id;
    private String name;
    private String brand;
    private String description;
    private Long price;
    private Integer inventory;
    private Category category;
    private List<Image> images;
}
