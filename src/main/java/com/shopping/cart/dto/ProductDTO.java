package com.shopping.cart.dto;

import com.shopping.cart.model.Category;
import com.shopping.cart.model.Image;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private String brand;
    private String description;
    private Long price;
    private Integer inventory;
    private Category category;
    private List<Image> images;
}
