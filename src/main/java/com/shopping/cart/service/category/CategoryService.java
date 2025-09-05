package com.shopping.cart.service.category;

import com.shopping.cart.dto.CategoryDTO;
import com.shopping.cart.exceptions.ResourceNotFoundException;
import com.shopping.cart.model.Category;
import com.shopping.cart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category cat) {
        Category category =  categoryRepository.findByName(cat.getName());
        if(category!=null){
            return category;
        }
        category = new Category();
        category.setName(category.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository :: delete,()->{ throw new ResourceNotFoundException("Category not found");});
    }

    @Override
    public Category updateCategory(CategoryDTO categoryDTO, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory->{
            oldCategory.setName(categoryDTO.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }
}
