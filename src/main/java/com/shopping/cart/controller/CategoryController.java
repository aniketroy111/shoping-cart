package com.shopping.cart.controller;


import com.shopping.cart.Response.ApiResponse;
import com.shopping.cart.dto.CategoryDTO;
import com.shopping.cart.exceptions.ResourceAlreadyExistException;
import com.shopping.cart.exceptions.ResourceNotFoundException;
import com.shopping.cart.model.Category;
import com.shopping.cart.service.category.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.AlreadyBoundException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Fetch Successfully",category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories = categoryService.getAllCategory();
            return ResponseEntity.ok(new ApiResponse("Fetch Successfully",categories));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        try {
            Category newCategory = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Added Successfully",newCategory));
        } catch (ResourceAlreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable String name){
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Fetch Successfully",category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(new ApiResponse("Delete Successfully",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody CategoryDTO categoryDTO){
        try {
            Category updateCategory = categoryService.updateCategory(categoryDTO,id);
            return ResponseEntity.ok(new ApiResponse("Updated Successfully",updateCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

}
