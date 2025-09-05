package com.shopping.cart.repository;

import com.shopping.cart.model.Category;
import com.shopping.cart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByName(String name);
}
