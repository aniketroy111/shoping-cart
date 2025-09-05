package com.shopping.cart.dto;

import com.shopping.cart.model.Product;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {

    private Long id;
    private String name;
}
