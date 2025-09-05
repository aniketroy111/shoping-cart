package com.shopping.cart.service.category;

import com.shopping.cart.dto.CategoryDTO;
import com.shopping.cart.model.Category;

import java.util.List;

public interface ICategoryService {

    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategory();
    Category addCategory(Category category);
    void deleteCategory(Long id);
    Category updateCategory(CategoryDTO categoryDTO,Long id);
}
